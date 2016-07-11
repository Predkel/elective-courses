<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
${message}
<sf:form modelAttribute="user" method="post">
  Document Id:   <sf:input path="documentId"/>
                 <sf:errors path="documentId" cssClass="error"/><br>
  Password:      <sf:input path="password"/>
                 <sf:errors path="password"/><br>
  First Name:    <sf:input path="firstName"/>
                 <sf:errors path="firstName"/><br>
  Last Name:     <sf:input path="lastName"/>
                 <sf:errors path="lastName"/><br>

                 <input type="submit" value="Register">
</sf:form>
</body>
</html>
