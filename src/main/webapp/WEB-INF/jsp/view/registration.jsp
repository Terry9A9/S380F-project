<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 10/4/2022
  Time: 4:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Registration</title>
</head>
<body>
    <p style="color: red">${error}</p>
    <form:form method="POST" enctype="multipart/form-data"
               modelAttribute="Student">
        <form:label path="username">User Name*</form:label><br />
        <form:input type="text" path="username" /><br /><br />
        <form:label path="password">Password*</form:label><br />
        <form:input type="password" path="password" /><br /><br />
        <form:label path="fullName">Full Name*</form:label><br />
        <form:input type="text" path="fullName" /><br /><br />
        <form:label path="phoneNumber">Phone Number*</form:label><br />
        <form:input type="number" path="phoneNumber" max="8"/><br /><br />
        <form:label path="address">Address*</form:label><br />
        <form:textarea path="address" rows="5" cols="30"/><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
        <c:url var="loginUrl" value="/login"/>
        <form action="${loginUrl}" method="get">
            <input type="submit" value="Already have an account" />
        </form>
</body>
</html>
