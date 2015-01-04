package com.app.todolst;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.app.todolst.ToDoFile;

public class ToDoRepository {

    public void ToDoRepository() {
	}
	
	private Key getUserKey() {
	    UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
	    Key userKey = KeyFactory.createKey("User", user.getNickname());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("User", userKey);
		Entity entUser = datastore.prepare(query).asSingleEntity();
		Key userKeyComplete = null;
		if(entUser == null)
		{
			entUser = new Entity("User", userKey);
			userKeyComplete = datastore.put(entUser);
		}
		else
		    userKeyComplete = entUser.getKey();
		
		return userKeyComplete;
	}
	
	private ToDoFile getFileFrmEntity(Entity toDoEnt) {
	    ToDoFile file = new ToDoFile();		
		if(toDoEnt != null)
		{
			file.setUser((User)toDoEnt.getProperty("user"));
			file.setDate((Date)toDoEnt.getProperty("date"));
			file.setName((String)toDoEnt.getProperty("name"));
			file.setContent((String)toDoEnt.getProperty("content"));
		}		
		return file;
	}
	
	private Entity getFileEntity(String strFileName) {
	    Filter fileNameFilter = new FilterPredicate("name", 
		                                            FilterOperator.EQUAL,
                                                    strFileName);
        Query query = new Query("ToDoFile").setFilter(fileNameFilter);
		query.setAncestor(getUserKey());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Entity> lstToDoFiles = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
		
		if(lstToDoFiles.size() <= 0)
		    return null;
			
		return lstToDoFiles.get(0);
	}
	
	public void save(ToDoFile file) {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		System.out.println("$$$ Username in save: " + file.getUser().getNickname());
		Entity entFile = new Entity("ToDoFile", getUserKey());
		entFile.setProperty("user", file.getUser());
		entFile.setProperty("date", file.getDate());
		entFile.setProperty("name", file.getName());
		entFile.setProperty("content", file.getContent());

		datastore.put(entFile);
	}
	
	public void updateFileContent(String strFileName, String strContent) {
	    Entity entFile = getFileEntity(strFileName);
		if(entFile == null)
		    return;
		
		entFile.setProperty("content", strContent);
		DatastoreServiceFactory.getDatastoreService().put(entFile);
	}	
	
	public List<ToDoFile> getAllFiles() {	
		Query query = query = new Query("ToDoFile").setAncestor(getUserKey());
		query.addSort("date", Query.SortDirection.DESCENDING);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Entity> lstToDoEnts = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
		
		List<ToDoFile> lstToDoFiles = new ArrayList<ToDoFile>();
		for(Entity toDoEnt : lstToDoEnts) {
			lstToDoFiles.add(getFileFrmEntity(toDoEnt));
		}
		
		return lstToDoFiles;
	}
	
	public ToDoFile getFile(String strFileName) {
	    Entity fileEntity = getFileEntity(strFileName);
		
		if(fileEntity == null)
		    return null;
			
		return getFileFrmEntity(fileEntity);
	}
}
