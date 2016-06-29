<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
${message}
<form method="post" action="${requestScope.processFormPath}">
  Document Id:   <input type="text" name="documentId"><br>
  Password:      <input type="password" name="password"><br>
  First Name:    <input type="text" name="firstName"><br>
  Last Name:     <input type="text" name="lastName">
                 <input type="hidden" name="operation" value="saveUser">
                 <input type="submit" value="Register">
</form>
</body>
</html>
