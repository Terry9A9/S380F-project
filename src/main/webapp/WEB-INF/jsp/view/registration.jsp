<%--
  Created by IntelliJ IDEA.
  WebUser: Terry
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
           modelAttribute="User">
    <form:label path="username">User Name*</form:label><br/>
    <form:input type="text" path="username" value="${UserInfo[0].username}"/><br/><br/>
    <form:label path="password">Password*</form:label><br/>
    <security:authorize access="isAnonymous()">
        <form:input type="password" path="password"/><br/><br/>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <form:input type="text" path="password" value="${UserInfo[0].password}"/><br/><br/>
    </security:authorize>
    <form:label path="fullName">Full Name*</form:label><br/>
    <form:input type="text" path="fullName" value="${UserInfo[0].fullName}"/><br/><br/>
    <form:label path="phoneNumber">phoneNumber</form:label><br/>
    <form:input type="number" path="phoneNumber" min="10000000" max="99999999" value="${UserInfo[0].phoneNumber}"/><br/><br/>
    <form:label path="address">Address*</form:label><br/>
    <form:input path="address" cssStyle="width: 700px" value="${UserInfo[0].address}"/><br/><br/>
    <security:authorize access="hasRole('ADMIN')">
        <form:label path="role">Role*</form:label><br/>
        <c:choose>
            <c:when test="${UserInfo[0].role eq 'ROLE_ADMIN'}">
                <form:radiobutton path="role" value="ROLE_USER" />ROLE_USER
                <form:radiobutton path="role" value="ROLE_ADMIN" checked="checked"/>ROLE_ADMIN<br/><br/>
            </c:when>
            <c:otherwise>
                <form:radiobutton path="role" value="ROLE_USER" checked="checked"/>ROLE_USER
                <form:radiobutton path="role" value="ROLE_ADMIN"/>ROLE_ADMIN<br/><br/>
            </c:otherwise>
        </c:choose>
    </security:authorize>
    <input type="submit" value="Submit"/>
</form:form>
    <c:url var="loginUrl" value="/login"/>
    <form action="${loginUrl}" method="get">
        <input type="submit" value="Back"/>
    </form>
</body>
</html>
