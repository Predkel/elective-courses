<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Authentication</title>
</head>
<body>
<h1>Authentication</h1>

${failedAttemptMessage}

<form method="post" action="${requestScope.processFormPath}">
  Document Id:  <input type="text" name="documentId"><br>
  Password:     <input type="password" name="password"><br>
                <input type="submit" value="Enter"><br>
</form>
<a href="${requestScope.processFormPath}?operation=register">Register</a>
</body>
</html>
