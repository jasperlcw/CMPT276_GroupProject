package com.example.myapplication;

import java.time.LocalDateTime;

/**
 * Stores information about replies to posts made by users.
 * @author Jasper Wong
 */

public class Reply {
	
	private User replyAuthor;
	private String replyContent;
	private LocalDateTime replyTime;
	private int replyID, parentPostID;
	
	
	public Reply(User replyAuthor, String replyContent, LocalDateTime replyTime, int replyID, int parentPostID) {
		this.replyAuthor = replyAuthor;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
		this.replyID = replyID;
		this.parentPostID = parentPostID;
	}


	public User getReplyAuthor() {
		return replyAuthor;
	}


	public int getReplyID() {
		return replyID;
	}


	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}


	public int getParentPostID() {
		return parentPostID;
	}


	public void setParentPostID(int parentPostID) {
		this.parentPostID = parentPostID;
	}


	public void setReplyAuthor(User replyAuthor) {
		this.replyAuthor = replyAuthor;
	}


	public String getReplyContent() {
		return replyContent;
	}


	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}


	public LocalDateTime getReplyTime() {
		return replyTime;
	}


	public void setReplyTime(LocalDateTime replyTime) {
		this.replyTime = replyTime;
	}
}
