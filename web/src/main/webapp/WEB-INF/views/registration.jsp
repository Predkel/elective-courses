<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
${message}
<sf:form modelAttribute="user" method="post">
  Document Id:   <sf:input path="documentId"/><br>
                 <sf:errors path="documentId"/>
  Password:      <sf:input path="password"/><br>
                 <sf:errors path="password"/>
  First Name:    <sf:input path="firstName"/><br>
                 <sf:errors path="firstName"/>
  Last Name:     <sf:input path="lastName"/>
                 <sf:errors path="lastName"/><br>

                 <input type="submit" value="Register">
</sf:form>
</body>
</html>
