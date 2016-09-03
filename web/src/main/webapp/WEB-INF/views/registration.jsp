<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
${message}
<s:url value="/js/loginValidator.js" var="loginValidator"/>
<script src="${loginValidator}" language="JavaScript"></script>
<sf:form modelAttribute="user" method="post">
    Document Id:   <sf:input path="documentId" id="login"/><comment id="loginMessage"></comment>
    <sf:errors path="documentId"/><br>
    Password:      <sf:password path="password"/>
    <sf:errors path="password"/><br>
    First Name:    <sf:input path="firstName"/>
    <sf:errors path="firstName"/><br>
    Last Name:     <sf:input path="lastName"/>
    <sf:errors path="lastName"/><br>

    <input type="submit" value="Register">
</sf:form>
</body>
</html>
