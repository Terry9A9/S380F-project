<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 4/5/2022
  Time: 6:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Poll</title>
</head>
<body>
<form:form method="POST" modelAttribute="Poll">
    <form:label path="question">Question: </form:label><br />
    <form:input type="text" path="question"/><br /><br />
    <form:label path="ans_a">A: </form:label><br />
    <form:input type="text" path="ans_a"/><br /><br />
    <form:label path="ans_b">B: </form:label><br />
    <form:input type="text" path="ans_b"/><br /><br />
    <form:label path="ans_c">C: </form:label><br />
    <form:input type="text" path="ans_c"/><br /><br />
    <form:label path="ans_d">D: </form:label><br />
    <form:input type="text" path="ans_d"/><br /><br />
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>
