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
    <title>Edit Lecture</title>
</head>
<body>
${lectureInfo[0].id}
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="lectureForm">
    <form:label path="lecture_num">Lecture number </form:label><br />
    <form:input type="number" path="lecture_num" min="1" value="${lectureInfo[0].lecture_num}"/><br /><br />
    <form:label path="title">Lecture title</form:label><br />
    <form:input path="title" type="text"  value="${lectureInfo[0].title}"/><br /><br />

        <b>Course Material:</b><br/>
        <ul>
            <c:forEach items="${lectureInfo[0].attachments}" var="attachment">
                <li>
                    <c:out value="${attachment.name}" />
                    [<a href="<c:url value="/course/${lectureInfo[0].course_id}/ID${lectureInfo[0].id}/delete/attachment/${attachment.id}" />">Delete</a>]
                </li>
            </c:forEach>
        </ul>
    <b>Add attachments</b><br />
    <input type="file" name="attachments" multiple="multiple"/><br/><br/>
    <input type="submit" value="Save"/>
</form:form>
<c:url var="ViewUrl" value="/course/${lectureInfo[0].course_id}/ID${lectureInfo[0].id}"/>
<form action="${ViewUrl}" method="get">
    <input type="submit" value="View"/>
</form>
</body>
</html>
