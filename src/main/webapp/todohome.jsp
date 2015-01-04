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
%>

<p>${fn:escapeXml(user.nickname)} (
    <a href="<%= userService.createLogoutURL("/todohome") %>">sign out</a>)</p>

<form action="/todoaddfile" method="post">
    <div><input type="text" name="filename"/></div>
    <div><input type="submit" value="Create"/></div>
</form>

<%
    List<ToDoFile> todofiles = (List<ToDoFile>)request.getAttribute("todofile");
    if (todofiles.isEmpty()) {
%>
<p> You are all done!!</p>
<%
} else {
%>
<p>Let's get to work - </p>
<%
    for (ToDoFile todofile : todofiles) {
        pageContext.setAttribute("filename", todofile.getName());
%>
<p><a href="/todoeditfile?filename=${fn:escapeXml(filename)}">${fn:escapeXml(filename)}</a></p>
<%
    }
}
%>

</body>
</html>
