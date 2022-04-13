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
    <form:input type="text" path="username" value="${UserInfo.username}"/><br/><br/>
    <form:label path="password">Password*</form:label><br/>
    <security:authorize access="isAnonymous()">
        <form:input type="password" path="password" value="${UserInfo.password}"/><br/><br/>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <form:input type="text" path="password" value="${UserInfo.password}"/><br/><br/>
    </security:authorize>
    <form:label path="fullName">Full Name*</form:label><br/>
    <form:input type="text" path="fullName" value="${UserInfo.fullName}"/><br/><br/>
    <form:label path="phoneNumber">Phone Number*</form:label><br/>
    <form:input type="number" path="phoneNumber" min="10000000" max="99999999" value="${UserInfo.phoneNumber}"/><br/><br/>
    <form:label path="address">Address*</form:label><br/>
    <form:textarea path="address" rows="5" cols="30" value="${UserInfo.address}"/><br/>
    <security:authorize access="hasRole('ADMIN')">
        <form:label path="roles">Roles</form:label><br/>
        <c:forEach items="${UserInfo.roles}" var="role" varStatus="status">
            <c:if test="${fn:contains(role, 'ROLE_USER')}">
                <c:set value="checked" var="userCheck"/>
            </c:if>
            <c:if test="${fn:contains(role, 'ROLE_ADMIN')}">
                <c:set value="checked" var="adminCheck"/>
            </c:if>
        </c:forEach>
        <form:checkbox path="roles" value="ROLE_USER" checked="${userCheck}"/>ROLE_USER
        <form:checkbox path="roles" value="ROLE_ADMIN" checked="${adminCheck}"/>ROLE_ADMIN<br/><br/>
    </security:authorize>
    <security:authorize access="isAnonymous()">
        <input type="hidden" name="roles" value="ROLE_USER"/>
    </security:authorize>
    <input type="submit" value="Submit"/>
</form:form>
    <c:url var="loginUrl" value="/login"/>
    <form action="${loginUrl}" method="get">
        <input type="submit" value="Back"/>
    </form>
</body>
</html>
