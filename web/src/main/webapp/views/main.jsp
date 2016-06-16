<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
  <form action="${requestScope.pathToProcessPagination}" method="post">
    <input type="text" name="maxResult" value="${requestScope.maxResult}">
    <input type="hidden" name="operation" value="main">
    <input type="hidden" name="paginationType" value="previous">
    <input type="submit" value="Number of courses on the page">
  </form><br>
  <c:choose>
    <c:when test="${sessionScope.teacher != null}"><jsp:include page="mainTeachers.jsp"/></c:when>
    <c:when test="${sessionScope.student != null}"><jsp:include page="mainStudents.jsp"/></c:when>
  </c:choose>
  <c:if test="${!requestScope.isTheFirstPage}">
    <a href="${requestScope.pathToProcessPagination}&maxResult=${requestScope.maxResult}&paginationType=previous">
      <-previous
    </a>
  </c:if>
  <c:if test="${!requestScope.isTheLastPage}">
    <a href="${requestScope.pathToProcessPagination}&maxResult=${requestScope.maxResult}&paginationType=next">
      next->
    </a>
  </c:if>
</body>
</html>
