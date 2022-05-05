<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
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
<h1>Poll</h1>
<security:authorize access="hasRole('ADMIN')">

</security:authorize>


<security:authorize access="hasRole('ADMIN')">
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
</security:authorize>
</body>
</html>
