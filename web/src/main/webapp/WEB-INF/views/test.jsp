<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
  <c:forEach items="${courses}" var="course">
    <c:out value="${course.title}"/>
  </c:forEach><br>
  <c:out value="${course2.title}"/>
  <c:out value="${course3.title}"/>
</body>
</html>
