<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Authentication</title>
</head>
<body>
<h1>Authentication</h1>

<s:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">
    <c:if test="${param.error != null}"> ·
        <p>
            Invalid username and password.
        </p>
    </c:if>
    <p>
        <label for="username"><s:message code="users.document.id"/> </label>
        <input type="text" id="username" name="username"/>
    </p>
    <p>
        <label for="password"><s:message code="users.password"/> </label>
        <input type="password" id="password" name="password"/>
    </p>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <button type="submit" class="btn"><s:message code="users.login"/> </button>
</form>
<s:url value="/students/new" var="studentsRegistration"/>
<a href="${studentsRegistration}">Registration for students</a><br>
<s:url value="/teachers/new" var="teachersRegistration"/>
<a href="${teachersRegistration}">Registration for teachers</a>
</body>
</html>
