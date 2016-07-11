<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><s:message code="courses"/></title>
</head>
<body>
<h1><s:message code="courses"/></h1>
<ul>
  <c:forEach items="${requestScope.courses}" var="course">
    <li>
      <s:url value="/students/course?courseId=${course.id}" var="showCourse"/>
      <a href="${showCourse}"><c:out value="${course.title}"/></a>
    </li>
  </c:forEach>
</ul>
</body>
</html>
