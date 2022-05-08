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
    <style>
        .comment_table {
            width: 100%;
            table-layout: fixed;
            border-collapse: collapse;
        }

        .comment_table tbody {
            display: block;
            width: 100%;
            overflow: auto;
            height: 500px;
        }

        .comment_table thead tr {
            display: block;
        }

        .comment_table thead {
            background: gray;
            color: #fff;
        }

        .comment_table th,
        .comment_table td {
            padding: 5px;
            text-align: left;
            width: 150px;
        }
    </style>
</head>
<body>
<form action="/" method="get" style="display: inline">
    <input type="submit" value="Home"/>
</form>
<c:url var="lectureUrl" value="${lectureInfo[0].course_code}/ID${lectureInfo[0].id}"/>
<security:authorize access="hasRole('ADMIN')">
    <c:url var="editUrl" value="/course/${lectureUrl}/edit"/>
    <form action="${editUrl}" method="get" style="display: inline">
        <input type="submit" value="Edit"/>
    </form>
</security:authorize>
<h1>Lecture ${lectureInfo[0].lecture_num}</h1>
<h2>Lecture Title: ${lectureInfo[0].title}</h2><br/>
<b>Course Material:</b><br/>
<ul>
    <c:forEach items="${lectureInfo[0].attachments}" var="attachment">
        <li>
            <c:out value="${attachment.name}"/>
            [<a href="<c:url value="/course/${lectureInfo[0].course_code}/ID${lectureInfo[0].id}/attachment/${attachment.id}" />">Download</a>]
        </li>
    </c:forEach>
</ul>

<c:url var="editUrl" value="/course/${lectureInfo[0].course_code}/ID${lectureInfo[0].id}/comment/add"/>
<form action="${editUrl}" method="get" style="display: inline">
    <input type="submit" value="Add comment"/>
</form>
<h1>[Comment List]</h1>
<c:choose>
<c:when test="${fn:length(lectureComments) == 0}">
    <h2>There are no comments in the system.</h2>
</c:when>
<c:otherwise>
<table class="comment_table" style="display: inline-table;">
    <thead>
    <tr>
        <th>Comment from:</th>
        <th>Comment:</th>
        <security:authorize access="hasRole('ADMIN')">
            <th>Action</th>
        </security:authorize>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${lectureComments}" var="comment">
    <tr>
        <td>${comment.user_name}</td>
        <td>${comment.comment}</td>
        <security:authorize access="hasRole('ADMIN')">
            <td><a href="/course/${lectureUrl}/delete/lectureComment/${comment.commentId}">[Delete]</a></td>
        </security:authorize>
    </tr>
</c:forEach>
</c:otherwise>
</c:choose>

</body>
</html>
