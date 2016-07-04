<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Authentication</title>
</head>
<body>
<h1>Authentication</h1>

<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post"> ¶
    <c:if test="${param.error != null}"> ·
        <p>
            Invalid username and password.
        </p>
    </c:if>
    <c:if test="${param.logout != null}"> ¸
        <p>
            You have been logged out.
        </p>
    </c:if>
    <p>
        <label for="username">Username</label>
        <input type="text" id="username" name="username"/> ¹
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password"/> º
    </p>
    <input type="hidden" »
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <button type="submit" class="btn">Log in</button>
</form>

<%--${message}--%>

<%--<form method="post" action="/login">--%>
  <%--Document Id:  <input type="text" name="documentId"><br>--%>
  <%--Password:     <input type="password" name="password"><br>--%>
                <%--<input type="submit" value="Enter"><br>--%>
<%--</form>--%>
<%--&lt;%&ndash;<a href="${requestScope.pathToProcessRegistration}">Register</a>&ndash;%&gt;--%>
</body>
</html>
