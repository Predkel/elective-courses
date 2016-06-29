<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
<h1>Courses</h1>
<%--failedAttemptMessage--%>
${message}
<ul>
  <c:forEach items="${requestScope.courses}" var="course">
    <li>
      <a href="${requestScope.pathToProcessCourseLink}&courseId=${course.id}"><c:out value="${course.title}"/></a>
    </li>
  </c:forEach>
</ul>
</body>
</html>
