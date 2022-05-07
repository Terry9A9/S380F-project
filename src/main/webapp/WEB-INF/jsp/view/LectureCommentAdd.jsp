<%-- 
    Document   : lectureCommentAdd
    Created on : 2022年5月4日, 下午9:06:28
    Author     : isaacchi
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add comment</title>
</head>
<body>
<form action="/" method="get">
    <input type="submit" value="Home"/>
</form>
<p style="color: red">${error}</p>
<form:form method="POST" modelAttribute="commentForm">
    <form:label path="comment">Your Comment:</form:label><br/>
    <form:input path="comment" type="text" min="1"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
