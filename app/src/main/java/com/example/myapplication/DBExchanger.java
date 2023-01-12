package com.example.myapplication;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * This class provides utilities to grab information from the SQL database for the project.
 * This utility uses the JDBC driver, which needs to be in the project's classpath for the tool to be functional.
 * Hardcoded to only work with a MySQL server on the local machine on port 3308.
 * @author Jasper Wong
 */
public class DBExchanger {
	
	//replace DOMAIN ip address with ip address of machine that is running MySQL
	//double check and make sure the port number is correct for MySQL
	private final String DOMAIN = "10.0.2.2";
	private final int PORT = 3308;
	private final String DB = "cmpt_276";
	
	Connection c;
	private boolean connectionStatus;
	
	/**
	 * The constructor for this object establishes a connection to the SQL database through JDBC.
	 */
	public DBExchanger() {
		try {
			
			//Loads the JDBC Driver
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("Cannot find the driver in the classpath!", e);
			}
			
			//url is "jdbc:mysql://localhost:3308/cmpt_276";
			String url = String.format("jdbc:mysql://%s:%d/%s", DOMAIN, PORT, DB);
			String user = "root";
			String pw = "";
			
			//Establish the connection
			c = DriverManager.getConnection(url, user, pw); 
			
			if (c != null) {
				System.out.println("Connection to database established.");
				
                DatabaseMetaData dm = (DatabaseMetaData) c.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
			
			connectionStatus = true;
		} catch (SQLException e) {
			System.out.println("Connection to database not established.");
			connectionStatus = false;
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a list of external links regarding a student from a given student number.
	 * @param stdNumber The student's student number.
	 * @return An ArrayList<String> of external links if available. If there are no links, or if something
	 * goes wrong when this method is running, then an ArrayList<String> of size 0 is returned.
	 */
	public ArrayList<String> getExternalLinks(int stdNumber) {
		String query = String.format("SELECT * FROM cmpt_276_user WHERE studentNumber = %d", stdNumber);
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			
			String fromSQL = rs.getString("externalLinks");
			
			if (fromSQL.length() == 0) { //that means there are no links in the DB
				return new ArrayList<String>(0);
			}
			
			return new ArrayList<String>(Arrays.asList(fromSQL.split(" ")));
		} catch (SQLException e) {
			System.out.println("Something went wrong with fetching external links list.");
			e.printStackTrace();
			
			return new ArrayList<String>(0);
		}
		
	}
	
	/**
	 * Adds an external link for the corresponding student to the DB. Make sure to update the User object itself in the client.
	 * @param toAdd The link to add. Must not contain a space within the URL. Spaces at the beginning and end are okay as the string is trimmed anyways.
	 * @param stdNumber The student number for the student that is updating the record.
	 */
	public void addExternalLink(String toAdd, int stdNumber) {
		
		toAdd = toAdd.trim();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cmpt_276_user WHERE studentNumber = " + stdNumber);
			rs.next();
			
			String existingList = rs.getString("externalLinks");
			
			if (existingList.length() != 0) {
				//add link to end of string if there are existing links
				//otherwise if there are no existing links just push the link to the DB
				toAdd = existingList + " " + toAdd;
			}
			
			//update the record
			String query = String.format("UPDATE cmpt_276_user SET externalLinks = '%s' WHERE studentNumber = %d", toAdd, stdNumber);
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with adding an external link to the DB.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Removes an external link for the corresponding student to the DB. Make sure to update the User object itself in the client.
	 * @param toRemove The link to remove. Must not contain a space within the URL. Spaces at the beginning and end are okay as the string is trimmed anyways.
	 * @param stdNumber The student number for the student that is updating the record.
	 * @return False if a link was not removed, true if a link is successfully removed.
	 */
	public boolean removeExternalLink(String toRemove, int stdNumber) {
		
		toRemove = toRemove.trim();
		
		try {
			Statement stmt = c.createStatement();
			
			ArrayList<String> links = getExternalLinks(stdNumber);
			
			if (!links.contains(toRemove)) {
				return false;
			} else {
				String toUpdate = "";
				links.remove(toRemove); //remove the link
				for (String s: links) {
					toUpdate = toUpdate + " " + s; //build back the string
				}
				
				toUpdate = toUpdate.trim(); //get rid of the first space
				String query = String.format("UPDATE cmpt_276_user SET externalLinks = '%s' WHERE studentNumber = %d", toUpdate, stdNumber);
				stmt.executeUpdate(query);
				
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with removing an external link to the DB.");
			e.printStackTrace();
			
			return false;
		}
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void close() {
		try {
			if (c != null && !c.isClosed()) {
				c.close();
			} else {
				System.out.println("There is no active connection to be closed.");
			}
			connectionStatus = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the status of the connection to the database.
	 * @return The status of the connection to the database. True if there is an active connection and false otherwise.
	 */
	public boolean getConnectionStatus() {
		return connectionStatus;
	}
	
	/**
	 * Verifies if the correct credentials are entered in to verify login into the service.
	 * Accepts a student number and the start date of the stay at the dormitories.
	 * @param studentNumber The student number to be entered.
	 * @param startDate The start date of the corresponding student's stay at the dormitory.
	 * @return True if the credentials entered in matches with the person listed in the database, and false otherwise.
	 */
	public boolean verifyLogin(int studentNumber, LocalDate startDate) {
		/*
		 * Grab the student number and check if the start date matches
		 * SQL Syntax: SELECT startDate FROM cmpt_276_user WHERE studentNumber = `java's student number`
		 */
		String query = String.format("SELECT startDate, isActive FROM cmpt_276_user WHERE studentNumber = %d", studentNumber);
		
		try {
			Statement stmt = c.createStatement();
			
			//Should only have 1 row for the result set as student numbers are unique per student
			ResultSet rs = stmt.executeQuery(query);
			
			rs.next();
			Date dbDate = rs.getDate("startDate");
			
			//both formats should be yyyy-mm-dd
			boolean loginStatus = startDate.toString().contentEquals(dbDate.toString()) && rs.getBoolean("isActive");
			return loginStatus;
		} catch (SQLException e) {
			System.out.println("Something went wrong with verifying the login.");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Searches and gets a list of posts with post titles that contains a substring of a search term. 
	 * @param searchTerm What the user wants to search for.
	 * @return An ArrayList containing data for Posts that have a substring that matches the substring.
	 * If there are no results, then the method returns an ArrayList of size 0.
	 */
	public ArrayList<Post> searchPosts(String searchTerm) {
		searchTerm = searchTerm.toUpperCase();
		//searches by post title for posts that contain search term in title
		String query = String.format("SELECT * FROM cmpt_276_post WHERE UPPER(postTitle) LIKE '%%%s%%'", searchTerm);
		ArrayList<Post> resultList;
		
		try {
			Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			//check if set is empty
			if (!rs.last()) {
				resultList = new ArrayList<Post>(0);
				return resultList; //return list of size 0 for no results
			} else {
				resultList = new ArrayList<Post>(rs.getRow()); //row number of last row for list size
			}
			
			rs.beforeFirst(); //reset cursor position
			while (rs.next()) {
				//search for the user
				int stdNumber = rs.getInt("studentNumber"); //get the student number from the post table

				User toPush = getStudentByNumber(stdNumber);
				
				//LocalDateTime postTime = rs.getTimestamp("postTime").toLocalDateTime(); //toLocalDateTime() method does not exist in android's library 
				LocalDateTime postTime = LocalDateTime.ofInstant(rs.getTimestamp("postTime").toInstant(), ZoneId.systemDefault());
				
				//push the post to the list
				resultList.add(new Post(toPush, rs.getString("postTitle"), rs.getString("postContent"), postTime, rs.getInt("postID"), rs.getString("pictureLink")));
			}
			
			return resultList;
		} catch (SQLException e) {
			System.out.println("Something went wrong with searching the post.");
			e.printStackTrace();
			
			resultList = new ArrayList<Post>(0);
			return resultList;
		}
	}
	
	/**
	 * Gets a list of posts sorted from newest to oldest. Note that row count starts at zero for first row.
	 * @param startingRow The post number for the starting post in the list.
	 * @param range The number of posts in the list.
	 * @return An ArrayList consisting of Posts with size denoted by the range and the first post denoted by the starting row number.
	 */
	public ArrayList<Post> getPostsByNumber(int startingRow, int range) {
		String query = String.format("SELECT * FROM cmpt_276_post ORDER BY postTime DESC LIMIT %d, %d ", startingRow, range);
		return getPostListByQuery(query);
	}
	
	/**
	 * Gets a list of posts made by a given student ordered from newest to oldest. Note that row count starts at zero for first row.
	 * @param studentNumber The student number for the author of the posts
	 * @return An ArrayList consisting of Posts made by the given student.
	 */
	public ArrayList<Post> getPostsByStudent(int studentNumber) {
		String query = String.format("SELECT * FROM cmpt_276_post WHERE studentNumber = %d ORDER BY postTime DESC", studentNumber);
		return getPostListByQuery(query);
	}
	
	/**
	 * A private helper method to assist in fetching an ArrayList of posts from the database.
	 * @param query The query to send to the database.
	 * @return An array list of posts returned by the database based on the query
	 */
	private ArrayList<Post> getPostListByQuery(String query) {
		ArrayList<Post> resultList;
		
		try {
			Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			if (!rs.last()) {
				resultList = new ArrayList<Post>(0);
				return resultList;
			} else {
				resultList = new ArrayList<Post>(rs.getRow());
			}
			rs.beforeFirst();
			
			while (rs.next()) {
				User toPush = getStudentByNumber(rs.getInt("studentNumber"));
				LocalDateTime postTime = LocalDateTime.ofInstant(rs.getTimestamp("postTime").toInstant(), ZoneId.systemDefault());
				
				resultList.add(new Post(toPush, rs.getString("postTitle"), rs.getString("postContent"), postTime, rs.getInt("postID"), rs.getString("pictureLink")));
			}
			
			return resultList;
		} catch (SQLException e) {
			System.out.println("Something went wrong with grabbing a list of posts by a student.");
			e.printStackTrace();
			
			resultList = new ArrayList<Post>(0);
			return resultList;
		}
	}
	
	/**
	 * Method for getting a post given the id of the post.
	 * @param id The id of the post.
	 * @return An object containing the post information, or null if an error occurs such as the database being unreachable.
	 * Null is also returned if an invalid id is inserted.
	 */
	public Post getPostByID(int id) {
		String query = String.format("SELECT * FROM cmpt_276_post WHERE postID = %d", id);
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if (!rs.next()) {
				return null;
			}
			
			User postAuthor = getStudentByNumber(rs.getInt("studentNumber"));
			LocalDateTime postTime = LocalDateTime.ofInstant(rs.getTimestamp("postTime").toInstant(), ZoneId.systemDefault());
			
			return new Post(postAuthor, rs.getString("postTitle"), rs.getString("postContent"), postTime, rs.getInt("postID"), rs.getString("pictureLink"));
		} catch (SQLException e) {
			System.out.println("Something went wrong with grabbing a post by post id number.");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Inserts a row into the database table for posts.
	 * @param toAdd The post to push onto the database table.
	 */
	public void addPost(Post toAdd) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(toAdd.getPostTime());
		
		//cannot handle null values
		String query = String.format("INSERT INTO cmpt_276_post (studentNumber, postTitle, postContent, postTime, postID, pictureLink) "
				+ "VALUES (%d, '%s', '%s', CAST(\"%s\" AS DATETIME), %d, '%s')", toAdd.getPostAuthor().getStudentNumber(), toAdd.getPostTitle(), toAdd.getPostContent(), formattedDate, toAdd.getPostID(), toAdd.getPictureLink());
		
		try {
			Statement stmt = c.createStatement();
			
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with adding a post.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts a row into the database table for posts given the post id.
	 * @param postID The id number of the post to delete from the database table
	 */
	public void deletePost(int postID) {
		String query = String.format("DELETE FROM cmpt_276_post WHERE postId = %d", postID);
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with deleting a post.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a student from the database with the given student number.
	 * @param stdNumber The student number of the student to search.
	 * @return An object of type User corresponding to the student number.
	 */
	public User getStudentByNumber(int stdNumber) {
		String query = String.format("SELECT * FROM cmpt_276_user WHERE studentNumber = %d", stdNumber);
		User toReturn = null; //make sure this is handled in the event that something wrong happens and the method returns null
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			
			LocalDate startDate = LocalDate.parse(rs.getDate("startDate").toString());
			LocalDate endDate = LocalDate.parse(rs.getDate("endDate").toString());
			
			ArrayList<String> externalLinks = getExternalLinks(stdNumber);
			
			toReturn = new User(stdNumber, rs.getString("dormNumber"), rs.getString("firstName"), rs.getString("middleName"), rs.getString("lastName"), 
					rs.getString("email"), rs.getString("phoneNumber"), externalLinks, startDate, endDate, rs.getBoolean("isAdmin"), rs.getBoolean("isActive"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	
	/**
	 * Adds a student into the database without external links and dorm number fields filled in. 
	 * The student pushed into the DB will never have the value of admin set to true.
	 * @param toAdd The student to add to the database.
	 */
	public void addStudent(User toAdd) {
		String query = String.format("INSERT INTO cmpt_276_user (studentNumber, firstName, middleName, lastName, phoneNumber, email, startDate, endDate, isAdmin) "
				+ "VALUES (%d, %s, %s, %s, %s, %s, CAST('%s' AS DATE), CAST('%s' AS DATE), 0)", toAdd.getStudentNumber(), toAdd.getFirstName(), toAdd.getMiddleName(), 
				toAdd.getLastName(), toAdd.getPhoneNumber(), toAdd.getEmail(), toAdd.getStartDate().toString(), toAdd.getEndDate().toString());
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with adding a user.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Disables students from the service where their end date is before the date specified.
	 * For example if the specified date is 2021-08-01, then if the date passed is greater than that the student will be disabled.
	 * @param date The specified date to check.
	 */
	public void removeStudent(LocalDate date) { //instead of running this inside of a java application, ideally this will be run as a cron job
		String query = String.format("UPDATE cmpt_276_user SET isActive = 0 WHERE endDate < CAST('%s' AS DATE)", date.toString());
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with removing a user.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Edits the email of a student.
	 * @param newEmail The email to be updated to.
	 * @param stdNumber The student number for the corresponding student.
	 */
	public void editEmail(String newEmail, int stdNumber) {
		
		String query = String.format("UPDATE cmpt_276_user SET email = '%s' WHERE studentNumber = %d", newEmail, stdNumber);
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with editing the email in the DB.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Edits the phone number of a student.
	 * @param newPhone The phone number to be updated to.
	 * @param stdNumber The student number for the corresponding student.
	 */
	public void editPhoneNumber(String newPhone, int stdNumber) {
		
		String query = String.format("UPDATE cmpt_276_user SET phoneNumber = '%s' WHERE studentNumber = %d", newPhone, stdNumber);
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with editing the phone number in the DB.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Edits the dorm number of a student.
	 * @param newDorm The dorm number to be updated to.
	 * @param stdNumber The student number for the corresponding student.
	 */
	public void editDormNumber(String newDorm, int stdNumber) {
		String query = String.format("UPDATE cmpt_276_user SET dormNumber = '%s' WHERE studentNumber = %d", newDorm, stdNumber);

		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with editing the phone number in the DB.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a list of replies to a post sorted from oldest to newest. Note that row count starts at zero for first row.
	 * @param parentID The ID number of the parent post.
	 * @return An ArrayList containing data for replies to a post ordered from oldest to newest.
	 * If there are no replies to the post, then the method returns an ArrayList of size 0.
	 */
	public ArrayList<Reply> getReplyByParent(int parentID) {
		String query = String.format("SELECT * FROM cmpt_276_reply WHERE parentPostID = %d ORDER BY replyTime ASC", parentID);
		ArrayList<Reply> toReturn;
		
		Statement stmt;
		try {
			stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			
			//check if set is empty
			if (!rs.last()) {
				toReturn = new ArrayList<Reply>(0);
				return toReturn;
			} else {
				toReturn = new ArrayList<Reply>(rs.getRow());
			}
			rs.beforeFirst(); //reset cursor position
			
			while (rs.next()) {
				int stdNumber = rs.getInt("studentNumber");
				User replyAuthor = getStudentByNumber(stdNumber);
				LocalDateTime postTime = LocalDateTime.ofInstant(rs.getTimestamp("replyTime").toInstant(), ZoneId.systemDefault());
				toReturn.add(new Reply(replyAuthor, rs.getString("replyContent"), postTime, rs.getInt("replyID"), rs.getInt("parentPostID")));
			}
			
			return toReturn;
		} catch (SQLException e) {
			System.out.println("Something went wrong with grabbing replies from the DB.");
			e.printStackTrace();
			
			toReturn = new ArrayList<Reply>(0);
			return toReturn;
		}
	}
	
	/**
	 * Inserts a row into the database table for replies to posts.
	 * @param toAdd The reply to the post to push to the database table.
	 */
	public void addReply(Reply toAdd) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(toAdd.getReplyTime());
		
		String query = String.format("INSERT INTO cmpt_276_reply (studentNumber, replyContent, replyTime, replyID, parentPostID) "
				+ "VALUES (%d, '%s', CAST(\"%s\" AS DATETIME), %d, %d)", toAdd.getReplyAuthor().getStudentNumber(), toAdd.getReplyContent(), formattedDate, toAdd.getReplyID(), toAdd.getParentPostID());
		
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something went wrong with adding a reply to the DB.");
			e.printStackTrace();
		}
	}
	
}
