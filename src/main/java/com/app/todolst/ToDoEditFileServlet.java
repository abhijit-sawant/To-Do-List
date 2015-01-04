package com.app.todolst;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.app.todolst.ToDoFile;
import com.app.todolst.ToDoRepository;

public class ToDoEditFileServlet extends HttpServlet {
  @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  
	  String strFileName = req.getParameter("filename");
	  String strContent = req.getParameter("filecontent");
	  
	  ToDoRepository repo = new ToDoRepository();
      repo.updateFileContent(strFileName, strContent);
	  
	  resp.sendRedirect("/todoeditfile?filename="+strFileName);
   }
   
   public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  
	  String strFileName = req.getParameter("filename");
	  
	  ToDoRepository repo = new ToDoRepository();
	  ToDoFile file = repo.getFile(strFileName);
	  String strContent = "";
	  if(file != null)
	      strContent = file.getContent();
		  
	  req.setAttribute("filename", strFileName);
	  req.setAttribute("filecontent", strContent);
      req.getRequestDispatcher("/todoeditfile.jsp").forward(req, resp);
   }   
}
