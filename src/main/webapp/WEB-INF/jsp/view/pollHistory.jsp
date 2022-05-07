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
    <title>Poll History</title>
</head>
<body>
<form action="/" method="get">
    <input type="submit" value="Home"/>
</form>

<h1>Poll History</h1>
<c:forEach items="${H}" var="a">
    <c:url var="url" value="/poll${a.poll_id}">${a.question}"></c:url>
    <li>
        <c:choose>
            <c:when test="${a.user_choice == 'a'}">
                (${a.course_code}) Poll Q: <a href="${url}">${a.question}</a>
                You choose (${a.user_choice}) ${a.ans_a}
            </c:when>

            <c:when test="${a.user_choice == 'b'}">
                (${a.course_code}) Poll Q: <a href="${url}">${a.question}</a>
                You choose (${a.user_choice}) ${a.ans_b}
            </c:when>

            <c:when test="${a.user_choice == 'c'}">
                (${a.course_code}) Poll Q: <a href="${url}">${a.question}</a>
                You choose (${a.user_choice}) ${a.ans_c}
            </c:when>

            <c:when test="${a.user_choice    == 'd'}">
                (${a.course_code}) Poll Q: <a href="${url}">${a.question}</a>
                You choose (${a.user_choice}) ${a.ans_d}
            </c:when>
        </c:choose>
    </li>
</c:forEach>

</body>
</html>
