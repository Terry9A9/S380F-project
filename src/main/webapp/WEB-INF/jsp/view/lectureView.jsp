<%--
  Created by IntelliJ IDEA.
  User: Terry
  Date: 15/4/2022
  Time: 4:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course Material Page</title>
</head>
<body>
<form action="/" method="get" style="display: inline">
    <input type="submit" value="Home"/>
</form>
<security:authorize access="hasRole('ADMIN')">
    <c:url var="editUrl" value="/course/${lectureInfo[0].course_code}/ID${lectureInfo[0].id}/edit"/>
    <form action="${editUrl}" method="get" style="display: inline">
        <input type="submit" value="Edit"/>
    </form>
</security:authorize>
    <h1>Lecture ${lectureInfo[0].lecture_num}</h1>
    <h2>Lecture Title: ${lectureInfo[0].title}</h2><br />
        <b>Course Material:</b><br/>
        <ul>
            <c:forEach items="${lectureInfo[0].attachments}" var="attachment">
                <li>
                    <c:out value="${attachment.name}" />
                    [<a href="<c:url value="/course/${lectureInfo[0].course_code}/ID${lectureInfo[0].id}/attachment/${attachment.id}" />">Download</a>]
                </li>
            </c:forEach>
        </ul>

</body>
</html>
