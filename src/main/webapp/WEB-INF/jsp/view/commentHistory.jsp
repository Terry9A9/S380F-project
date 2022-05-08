<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 8/5/2022
  Time: 2:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Comment History</title>
</head>
<body>
<form action="/" method="get">
    <input type="submit" value="Home"/>
</form>
<h1>Lecture Comment History</h1>
<c:forEach items="${cH}" var="c">
    <li>
        [${c.course_code}] Lecture ${c.lecture_num} Comment: <a
            href="/course/${c.course_code}/ID${c.lectureId}">${c.comment}</a>
    </li>
</c:forEach>
<br/>
<h1>Poll Comment History</h1>
<c:forEach items="${cpH}" var="d">
    <li>
        POLL ${d.poll_id} Comment: <a href="/poll${d.poll_id}">${d.comment}</a>
    </li>
</c:forEach>
</body>
</html>
