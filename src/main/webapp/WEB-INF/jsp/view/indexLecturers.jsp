<%--
  Created by IntelliJ IDEA.
  WebUser: Terry
  Date: 10/4/2022
  Time: 12:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Online Course Website</title>
    <style>
        .user_table {
            width: 100%;
            table-layout: fixed;
            border-collapse: collapse;
        }
        .user_table tbody {
            display: block;
            width: 100%;
            overflow: auto;
            height: 100px;
        }
        .user_table thead tr {
            display: block;
        }
        .user_table thead {
            background: gray;
            color: #fff;
        }
        .user_table th, .user_table td {
            padding: 5px;
            text-align: left;
            width: 150px;
        }

        .fixed_header {
            width: 50%;
            table-layout: fixed;
            border-collapse: collapse;
            padding: 5px;
        }

        .fixed_header tbody {
            width: 50%;
            height: 50px;
        }

        .fixed_header thead {
            background: gray;
            color: #fff;
        }

        .fixed_header th {
            padding: 5px;
            text-align: left;
        }

        .fixed_header td {
            padding: 5px;
            text-align: left;
        }

    </style>
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

<security:authorize access="hasRole('ADMIN')">
    <c:if test="${param.successful != null}">
        <p style="color: green">Delete Successful</p>
    </c:if>
    <c:if test="${param.editSuccessful != null}">
        <p style="color: green">Edit Successful</p>
    </c:if>
    <c:if test="${param.addSuccessful != null}">
        <p style="color: green">Add Successful</p>
    </c:if>
    <a href="/user/registration">Add new user</a><br/>
    <c:choose>
        <c:when test="${fn:length(Users) == 0}">
            <h2>There are no users in the system.</h2>
        </c:when>
        <c:otherwise>
            <div>
                <table class="user_table">
                    <thead>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Full Name</th>
                        <th>Address</th>
                        <th>Roles</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${Users}" var="user">
                        <tr>
                            <td><a href="<c:url value="/user/edit/${user.username}"/>"> ${user.username}</a></td>
                            <td>${user.password}</td>
                            <td>${user.fullName}</td>
                            <td>${user.address}</td>
                            <td>${user.role}</td>
                            <td>
                                [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </c:otherwise>
    </c:choose>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
    <br/><br/>
    <c:url var="addCourseUrl" value="/index/addCourse"/>
    <form action="${addCourseUrl}" method="get">
        <input type="submit" value="Add New Course"/>
    </form>
</security:authorize>

<c:choose>
    <c:when test="${fn:length(Courses) == 0}">
        <h2>There are no Course in the system.</h2>
    </c:when>
    <c:otherwise>
        <c:forEach items="${Courses}" var="course">
        <div>
            <h3>${course.course_name} (${course.course_code})</h3>
            <security:authorize access="hasRole('ADMIN')">
                <a href="/course/${course.course_code}/add">Add New Lecture</a>
                <a href="/course/${course.course_code}/addPoll" style="float: right;">Add New Poll</a>
                <br/>
            </security:authorize>
            <table class="fixed_header" style="display: inline-table;">
                <thead>
                <tr>
                    <th>Lecture Number</th>
                    <th>Lecture Title</th>
                    <security:authorize access="hasRole('ADMIN')">
                        <th>Action</th>
                    </security:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${Lectures}" var="lecture">
                    <c:if test="${lecture.course_code == course.course_code}">
                        <tr>
                            <td>${lecture.lecture_num}</td>
                            <td><a href="/course/${course.course_code}/ID${lecture.id}">${lecture.title}</a></td>
                            <security:authorize access="hasRole('ADMIN')">
                                <td>
                                    <a href="/course/${course.course_code}/delete/ID${lecture.id}">[Delete]</a>
                                    <a href="/course/${course.course_code}/ID${lecture.id}/edit">[Edit]</a>
                                </td>
                            </security:authorize>

                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table >
            <table style="float: right;" class="fixed_header">
                <thead>
                <tr>
                    <td>Poll</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Question1</td>
                </tr>
                </tbody>
            </table>
        </div>
        </c:forEach>
    </c:otherwise>
</c:choose>

</body>
</html>
