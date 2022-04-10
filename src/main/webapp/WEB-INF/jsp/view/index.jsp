<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 10/4/2022
  Time: 12:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Online Course Website</title>
</head>
<body>
    i am a index
    <security:authorize access="isAuthenticated()">
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </security:authorize>

    <security:authorize access="isAnonymous()">
        <c:url var="loginUrl" value="/login"/>
        <form action="${loginUrl}" method="get">
            <input type="submit" value="log in" />
        </form>
    </security:authorize>
</body>
</html>
