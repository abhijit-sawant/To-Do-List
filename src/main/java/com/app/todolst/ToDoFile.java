package com.app.todolst;

import java.util.Date;

import com.google.appengine.api.users.User;

public class ToDoFile {
	private User   mUser = null;
	private Date   mDate = null;
	private String mStrName = "";
	private String mStrContent = "";
	
	public ToDoFile() {
	}
	
	public ToDoFile(User user, Date date, String strName) {
	    mUser = user;
		mDate = date;
		mStrName = strName;
	}
	
	public void setUser(User user) {
	    mUser = user;
	}	
	
	public User getUser() {
	    return mUser;
	}
	
	public void setDate(Date date) {
	    mDate = date;
	}		
	
	public Date getDate() {
	    return mDate;
	}	
	
	public void setName(String strName) {
	    mStrName = strName;
	}
	
	public String getName() {
	    return mStrName;
	}
	
	public void setContent(String strContent) {
	    mStrContent = strContent;
	}
	
	public String getContent() {
	    return mStrContent;
	}	
}
