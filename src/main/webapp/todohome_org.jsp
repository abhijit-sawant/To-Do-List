<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
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
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>)</p>

<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key userToDoKey = KeyFactory.createKey("UserToDoList", user.getNickname());
    Query query = new Query("ToDoItem", userToDoKey).addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> todoitems = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (todoitems.isEmpty()) {
%>
<p> You are all done!!</p>
<%
} else {
%>
<p>Let's get to work - </p>
<%
    for (Entity todoitem : todoitems) {
        pageContext.setAttribute("todoitem_content",
                todoitem.getProperty("content"));
%>
<blockquote>${fn:escapeXml(todoitem_content)}</blockquote>
<%
    }
}
%>

<form action="/todohome" method="post">
    <div><input type="text" name="todoitem"/></div>
    <div><input type="submit" value="Add"/></div>
</form>

</body>
</html>
