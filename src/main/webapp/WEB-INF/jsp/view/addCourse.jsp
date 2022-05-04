<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 15/4/2022
  Time: 7:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Course</title>
</head>
<body>
<c:url var="homeUrl" value="/"/>
<form action="${homeUrl}" method="get">
    <input type="submit" value="Home"/>
</form>
<p style="color: red">${error}</p>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="Course">
    <form:label path="course_code">Course Code*</form:label><br/>
    <form:input type="text" path="course_code"/><br/><br/>
    <form:label path="course_name">Course Name*</form:label><br/>
    <form:input type="text" path="course_name"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
