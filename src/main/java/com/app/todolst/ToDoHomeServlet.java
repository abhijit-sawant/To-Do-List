package com.app.todolst;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.app.todolst.ToDoFile;
import com.app.todolst.ToDoRepository;

public class ToDoHomeServlet extends HttpServlet {
  @Override
   public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  
	  ToDoRepository repo = new ToDoRepository();
	  List<ToDoFile> lstToDoFiles = repo.getAllFiles();
	  req.setAttribute("todofile", lstToDoFiles);
      req.getRequestDispatcher("/todohome.jsp").forward(req, resp);
   }
}
