package com.example.myapplication;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Stores information about the user using the service.
 * @author Jasper Wong
 */
public class User {
	private int studentNumber;
	private String dormNumber, firstName, middleName, lastName, email, phoneNumber;
    private ArrayList<String> externalLinks;
    private LocalDate startDate, endDate;
    private boolean isAdmin, isActive;
	
    /**
     * The constructor for the object to store user information.
     * In the case that no data exists for a field, such as a student's middle name,
     * then null will be used as the input for the parameter.
     * @param studentNumber A user's student number.
     * @param dormNumber The student's address.
     * @param firstName The student's first name.
     * @param middleName The student's middle name.
     * @param lastName The student's last name.
     * @param email The student's email.
     * @param phoneNumber The student's phone number.
     * @param externalLinks A list containing external links to where a student can be contacted at.
     * @param startDate The start date of the student's stay in the dorm.
     * @param duration The number of days the student is staying in the dorm.
	 * @param isAdmin Value for if the user is a system administrator.
	 * @param isActive Value for if the account is active. False means the user is not allowed access to the service.
     */
	public User(int studentNumber, String dormNumber, String firstName, String middleName, String lastName,
				String email, String phoneNumber, ArrayList<String> externalLinks, LocalDate startDate, int duration, boolean isAdmin, boolean isActive) {
	    this.studentNumber = studentNumber;
	    this.dormNumber = dormNumber;
	    this.firstName = firstName;
	    this.middleName = middleName;
	    this.lastName = lastName;
	    this.email = email;
	    this.phoneNumber = phoneNumber;
	    this.externalLinks = new ArrayList<String>(externalLinks);
	    this.startDate = startDate;
	    this.endDate = startDate.plusDays(duration);
	    this.isAdmin = isAdmin;
	    this.isActive = isActive;
	}
	
	/**
     * The constructor for the object to store user information.
     * In the case that no data exists for a field, such as a student's middle name,
     * then null will be used as the input for the parameter.
     * @param studentNumber A user's student number.
     * @param dormNumber The student's address.
     * @param firstName The student's first name.
     * @param middleName The student's middle name.
     * @param lastName The student's last name.
     * @param email The student's email.
     * @param phoneNumber The student's phone number.
     * @param externalLinks A list containing external links to where a student can be contacted at.
     * @param startDate The start date of the student's stay in the dorm.
     * @param endDate The end date of the student's stay in the dorm.
	 * @param isAdmin Value for if the user is a system administrator.
	 * @param isActive Value for if the account is active. False means the user is not allowed access to the service.
     */
	public User(int studentNumber, String dormNumber, String firstName, String middleName, String lastName,
			String email, String phoneNumber, ArrayList<String> externalLinks, LocalDate startDate, LocalDate endDate, boolean isAdmin, boolean isActive) {
		this.studentNumber = studentNumber;
		this.dormNumber = dormNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.externalLinks = new ArrayList<String>(externalLinks);
		this.startDate = startDate;
		this.endDate = endDate;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
	}
	
	/**
	 * The copy constructor for the object to store user information.
	 * @param other The other user object which is to be copied.
	 */
	public User(User other) {
	    this.studentNumber = other.studentNumber;
	    this.dormNumber = other.dormNumber;
	    this.firstName = other.firstName;
	    this.middleName = other.middleName;
	    this.lastName = other.lastName;
	    this.email = other.email;
	    this.phoneNumber = other.phoneNumber;
	    this.externalLinks = new ArrayList<String>(other.externalLinks);
	    this.startDate = other.startDate;
	    this.endDate = other.endDate;
	    this.isAdmin = other.isAdmin;
	    this.isActive = other.isActive;
	}
	
	/**
	 * Gets the user's student number.
	 * @return This student's student number.
	 */
	public int getStudentNumber() {
	    return this.studentNumber;
	}
	
	/**
	 * Gets the student's address.
	 * @return This student's address.
	 */
	public String getAddress() {
	    return this.dormNumber;
	}
	
	/**
	 * Gets the student's first name.
	 * @return This student's first name.
	 */
	public String getFirstName() {
	    return this.firstName;
	}
	
	/**
	 * Get's the student's middle name. 
	 * Will return null if the student does not have a middle name.
	 * @return This student's middle name, or null if the student does not have a middle name on record.
	 */
	public String getMiddleName() {
	    return this.middleName;
	}
	
	/**
	 * Get's the student's last name.
	 * @return This student's last name.
	 */
	public String getLastName() {
	    return this.lastName;
	}
	
	/**
	 * Get's the student's email.
	 * @return This student's email.
	 */
	public String getEmail() {
	    return this.email;
	}
	
	/**
	 * Get's the student's phone number.
	 * @return This student's phone number, or null if not applicable.
	 */
	public String getPhoneNumber() {
	    return this.phoneNumber;
	}
	
	/**
	 * Gets a list of external links where a student can be contacted.
	 * @return A copy of a list of external links to contact a student.
	 */
	public ArrayList<String> getExternalLinks() {
		return new ArrayList<String>(this.externalLinks);
	}
	
	/**
	 * Gets the start date of the student's stay at the dorms.
	 * @return This student's start date of the student's stay at the dorms.
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Gets the end date of the student's stay at the dorms.
	 * @return This student's end date of the student's stay at the dorms.
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}

	/**
	 * Gets the status of whether a user is a system administrator or not.
	 * @return The boolean value for which a user is an admin or not.
	 */
	public boolean adminStatus() {
		return this.isAdmin;
	}
	
	/**
	 * Sets the student number of a student.
	 * @param studentNumber The student number to be set.
	 */
	public void setUserID(int studentNumber) {
	    this.studentNumber = studentNumber;
	}
	
	/**
	 * Sets the dorm number of a student.
	 * @param dormNumber The updated address to be set. 
	 */
	public void setDormNumber(String dormNumber) {
	    this.dormNumber = dormNumber;
	}
	
	/**
	 * Sets the first name of a student.
	 * @param firstName The first name of the student to be set.
	 */
	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	}
	
	/**
	 * Sets the middle name of a student.
	 * @param middleName The middle name of a student to be set.
	 */
	public void setMiddleName(String middleName) {
	    this.middleName = middleName;
	}
	
	/**
	 * Sets the last name of a student.
	 * @param lastName The last name of a student to be set.
	 */
	public void setLastName(String lastName) {
	    this.lastName = lastName;
	}
	
	/**
	 * Sets the email of a student.
	 * @param email The email of a student to be set.
	 */
	public void setEmail(String email) {
	    this.email = email;
	}
	
	/**
	 * Sets the phone number of a student.
	 * @param phoneNumber The phone number of a student to be set.
	 */
	public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Sets the start date of a student's stay at the dorms.
	 * @param startDate The start date of the student's stay at the dorms to be set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Sets the end date of a student's stay at the dorms.
	 * @param endDate The end date of the student's stay at the dorms to be set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Removes a URL link to an external source for the student.
	 * @param toRemove The exact URL of the link to be removed.
	 */
	public boolean removeExternalLink(String toRemove) {
	    return externalLinks.remove(toRemove);
	}
	
	/**
	 * Adds a URL link to an external source for the student.
	 * @param toAdd The exact URL of the link to be added.
	 */
	public void addExternalLink(String toAdd) {
	    externalLinks.add(toAdd);
	}

	/**
	 * Sets the status denoting if a user is a system administrator or not.
	 * @param isAdmin The value to be set.
	 */
	public void setAdminStatus(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	/**
	 * Gets the status of the account of the student. True if the student is allowed access into the service false otherwise.
	 * @return The status of the student's account.
	 */
	public boolean getIsActive() {
		return this.isActive;
	}
	
	/**
	 * Gets the status of the account of the student. True if the student is allowed access into the service false otherwise.
	 * @param isActive The status of the student's account.
	 */
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
