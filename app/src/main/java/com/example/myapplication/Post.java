package com.example.myapplication;

import java.time.LocalDateTime;
import java.util.Random;


/**
 * Stores information about the posts made by users.
 * @author Sean Brown
 */

public class Post {
    private User postAuthor;
    private String postTitle, postContent;
    private LocalDateTime postTime;
    private int postID;
    private String pictureLink;



    /**
     * The constructor for the object to create and store post information.
     * In the case that a user doesn't leaves either Content or Field empty, then null will be used as the parameter.
     * This constructor generates a random number for the Id number of the post.
     * Should be used to create new posts to push to the database table.
     * @param postAuthor User who created the post.
     * @param postTitle Title given to post by the author.
     * @param postContent Content of post written by the author.
     * @param postTime Date and time post was uploaded.
     * Generates random number between 0-999999 for post ID.
     */

    public Post(User postAuthor, String postTitle, String postContent, LocalDateTime postTime, String pictureLink) {
//        this.postAuthor = postAuthor;
//        this.postTitle = postTitle;
//        this.postContent = postContent;
//        this.postTime = postTime;
//        Random rand = new Random();
//        this.postID = rand.nextInt(999999);
    	
    	this(postAuthor, postTitle, postContent, postTime, (new Random().nextInt(999999)), pictureLink);
    }
    
    /**
     * The constructor for the object to create and store post information.
     * In the case that a user doesn't leaves either Content or Field empty, then null will be used as the parameter.
     * @param postAuthor User who created the post.
     * @param postTitle Title given to post by the author.
     * @param postContent Content of post written by the author.
     * @param postTime Date and time post was uploaded.
     * Generates random number between 0-999999 for post ID.
     */
    public Post(User postAuthor, String postTitle, String postContent, LocalDateTime postTime, int postID, String pictureLink) {
    	this.postAuthor = postAuthor;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTime = postTime;
        this.postID = postID;
        this.pictureLink = pictureLink;
    }

	/**
     * Gets the post Author.
     * @return the author of the post.
     */

    public User getPostAuthor() {
        return postAuthor;
    }

    /**
     * Gets the post Title.
     * @return title of the post, or null if no title was given.
     */

    public String getPostTitle() {
        return postTitle;
    }

    /**
     * Gets the post Content.
     * @return content of the post, or null if no content was given.
     */

    public String getPostContent() {
        return postContent;
    }
    /**
     * Gets the post Time.
     * @return time post was made.
     */

    public LocalDateTime getPostTime() {
        return postTime;
    }
    /**
     * Gets the randomly generated post ID.
     * @return ID of the post.
     */

    public int getPostID() {
        return postID;
    }

    /**
     * Gets the URL of a link to the picture of a post.
     * @return URL to a picture.
     */
    public String getPictureLink() {
    	return pictureLink;
    }
    
    /**
     * Sets the author of a post.
     * @param postAuthor The updated author to be set.
     */
    public void setPostAuthor(User postAuthor) {
        this.postAuthor = postAuthor;
    }
    /**
     * Sets the title of a post.
     * @param postTitle The updated title to be set.
     */

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    /**
     * Sets the content of a post.
     * @param postContent The updated content to be set.
     */

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    /**
     * Sets the time of a post.
     * @param postTime The updated time to be set.
     */

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }
    /**
     * Sets the ID of a post.
     * @param postID The updated ID to be set.
     */

    public void setPostID(int postID) {
        this.postID = postID;
    }
    
    /**
     * Sets the URL of a link to the picture of a post.
     * @return URL to a picture.
     */
    public void setPictureLink(String pictureLink) {
    	this.pictureLink = pictureLink;
    }
}
