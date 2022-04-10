<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 10/4/2022
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h1>Login</h1>

<p style="color: ${color}">${info}</p>

<form action="login" method='POST'>
    User: <input type='text' name='username'><br />
    Password: <input type='password' name='password' /><br />
    Remember Me: <input type="checkbox" name="remember-me" /><br />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input name="submit" type="submit" value="Log In" /><br />
</form>
<c:url var="registration" value="/registration"/>
<a href="${registration}">New Student Registration</a>
</body>
</html>