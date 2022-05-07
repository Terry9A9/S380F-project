<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 7/5/2022
  Time: 10:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<security:authorize access="isAuthenticated()">
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Log out"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</security:authorize>

<security:authorize access="isAnonymous()">
    <c:url var="loginUrl" value="/login"/>
    <form action="${loginUrl}" method="get">
        <input type="submit" value="log in"/>
    </form>
</security:authorize>
<h1>Poll History</h1>
<c:forEach items="${H}" var="a">
    <li>
        Poll ${a.poll_id} You choice ${a.user_choice}
    </li>
</c:forEach>

</body>
</html>
