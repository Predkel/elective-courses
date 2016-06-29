<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add course</title>
</head>
<body>
<form method="post" action="${requestScope.processFormPath}">
  Title:        <input type="text" name="title">
  ${message}
  <br>
  Description:  <textarea cols="20" rows="20" name="description"></textarea><br>
  <input type="hidden" name="operation" value="saveCourse">
  <input type="submit" value="Add course">
</form>
</body>
</html>
