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
        .fixed_header {
            width: 1000px;
            table-layout: fixed;
            border-collapse: collapse;
        }

        .fixed_header tbody {
            display: block;
            width: 100%;
            overflow: auto;
            height: 100px;
        }

        .fixed_header thead tr {
            display: block;
        }

        .fixed_header thead {
            background: gray;
            color: #fff;
        }

        .fixed_header th, .fixed_header td {
            padding: 5px;
            text-align: left;
            width: 150px;
        }
    </style>
</head>
<body>
i am a index
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
    <a href="/user/registration">Add new user</a><br/>
    <c:choose>
        <c:when test="${fn:length(Users) == 0}">
            <i>There are no users in the system.</i>
        </c:when>
        <c:otherwise>
            <table class="fixed_header">
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
        </c:otherwise>
    </c:choose>
</security:authorize>

</body>
</html>
