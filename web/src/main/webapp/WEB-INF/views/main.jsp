<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
  <form action="${requestScope.pathToToMain}" method="get">
    <input type="hidden" name="operation" value="main">
    <input type="text" name="maxResult" value="${requestScope.maxResult}">
    <input type="hidden" name="firstResult" value="${requestScope.firstResult}">
    <input type="submit" value="Number of courses on the page">
  </form><br>
  <c:forEach items="${requestScope.numbersOfPages}" var="pageNumber">
    <c:choose>
      <c:when test="${pageNumber == requestScope.currentPage}">
        <c:out value="${pageNumber}"/>
      </c:when>
      <c:otherwise>
        <a href="${requestScope.pathToMain}&pageNumber=${pageNumber}&maxResult=${requestScope.maxResult}">
          <c:out value="${pageNumber}"/>
        </a>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:choose>
    <c:when test="${sessionScope.teacher != null}"><jsp:include page="mainTeachers.jsp"/></c:when>
    <c:when test="${sessionScope.student != null}"><jsp:include page="mainStudents.jsp"/></c:when>
  </c:choose>
</body>
</html>
