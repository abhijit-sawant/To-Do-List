<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.app.todolst.ToDoFile" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
		}
	
	String strFileName = (String)request.getAttribute("filename");
	if(strFileName == null)
		strFileName = "";	
	pageContext.setAttribute("filename", strFileName);
	
	String strFileContent = (String)request.getAttribute("filecontent");
	if(strFileContent == null)
		strFileContent = "";	
	pageContext.setAttribute("filecontent", strFileContent);	
%>

<p>${fn:escapeXml(user.nickname)} (
    <a href="<%= userService.createLogoutURL("/todohome") %>">sign out</a>) 
	<a href="/todohome">Home</a>	
</p>

<p>File Name : ${fn:escapeXml(filename)}</p>
<form action="/todoeditfile" method="post">
    <div><textarea name="filecontent" rows="4" cols="50" placeholder="What are you up to?">${fn:escapeXml(filecontent)}</textarea></div>
    <div><input type="submit" value="Save"/></div>
	<div><input type="hidden" name="filename" value=${fn:escapeXml(filename)}></div>
</form>

</body>
</html>
