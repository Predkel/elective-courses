<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html>
<head>
  <title>Courses</title>
</head>
<body>
  <form method="get">
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
        <a href="?pageNumber=${pageNumber}&maxResult=${requestScope.maxResult}">
          <c:out value="${pageNumber}"/>
        </a>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <tiles:insertAttribute name="content"/>
</body>
</html>
