<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Poll</title>
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
<form action="/" method="get">
    <input type="submit" value="Home"/>
</form>

<h1>Poll</h1>

    <h1>Question: ${poll.question}</h1>
    <form:form method="post" modelAttribute="poll_choice" action="">

        <c:if test="${not empty poll.ans_a}">
            <form:label path="user_choice">${poll.ans_a} </form:label>
            <form:radiobutton path="user_choice" value="a" checked="${Choose_a}"/>
            <c:if test="${not empty checked}">
                Vote count: ${findChoice_a}/${findChoice_a+findChoice_b+findChoice_c+findChoice_d}
            </c:if>
            <br/>
        </c:if>

        <c:if test="${not empty poll.ans_b}">
            <form:label path="user_choice">${poll.ans_b} </form:label>
            <form:radiobutton path="user_choice" value="b" checked="${Choose_b}"/>
            <c:if test="${not empty checked}">
                Vote count: ${findChoice_b}/${findChoice_a+findChoice_b+findChoice_c+findChoice_d}
            </c:if>
            <br/>
        </c:if>

        <c:if test="${not empty poll.ans_c}">
            <form:label path="user_choice">${poll.ans_c} </form:label>
            <form:radiobutton path="user_choice" value="c" checked="${Choose_c}"/>
            <c:if test="${not empty checked}">
                Vote count: ${findChoice_c}/${findChoice_a+findChoice_b+findChoice_c+findChoice_d}
            </c:if>
            <br/>
        </c:if>

        <c:if test="${not empty poll.ans_d}">
            <form:label path="user_choice">${poll.ans_d} </form:label>
            <form:radiobutton path="user_choice" value="d" checked="${Choose_d}"/>
            <c:if test="${not empty checked}">
                Vote count: ${findChoice_d}/${findChoice_a+findChoice_b+findChoice_c+findChoice_d}
            </c:if>
            <br/>
        </c:if>

        <br/>
        <input type="submit" name="Vote" value="Vote"/>
    </form:form>
<br/><br/>
    <c:url var="editUrl" value="/poll${poll.id}/comment/add"/>
    <form action="${editUrl}" method="get" style="display: inline">
        <input type="submit" value="Add comment"/>
    </form>
<h1>[Comment List]</h1>
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
    <c:choose>
        <c:when test="${fn:length(pollComments) == 0}">
            <h2>There are no comments in the system.</h2>
        </c:when>
        <c:otherwise>
            <c:forEach items="${pollComments}" var="comment">
                <tr>
                    <td>${comment.user_name}</td>
                    <td>${comment.comment}</td>
                    <security:authorize access="hasRole('ADMIN')">
                        <td><a href="/poll${poll.id}/delete/pollComment/${comment.poll_cId}">[Delete]</a></td>
                    </security:authorize>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

</body>
</html>
