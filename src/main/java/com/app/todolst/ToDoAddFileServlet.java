package com.app.todolst;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.app.todolst.ToDoFile;

public class ToDoAddFileServlet extends HttpServlet {
	
	private ToDoRepository mRepo = new ToDoRepository();

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String strFileName = req.getParameter("filename");
    
	ToDoFile file = new ToDoFile(user, new Date(), strFileName);
    mRepo.save(file);

    resp.sendRedirect("/todoeditfile?filename="+strFileName);
  }
}
