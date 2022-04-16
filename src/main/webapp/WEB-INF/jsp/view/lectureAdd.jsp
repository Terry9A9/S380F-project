<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 15/4/2022
  Time: 12:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add lecture</title>
</head>
<body>
<p style="color: red">${error}</p>
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="lectureForm">
    <form:label path="lecture_num">Lecture number </form:label><br />
    <form:input type="number" path="lecture_num" min="1"/><br /><br />
    <form:label path="title">Lecture title</form:label><br />
    <form:input path="title" type="text"/><br /><br />
    <b>Course Material:</b><br />
    <input type="file" name="attachments" multiple="multiple" /><br /><br />
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
