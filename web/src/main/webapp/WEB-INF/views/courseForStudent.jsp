<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><c:out value="${course.title}"/></title>
</head>
<body>
<h1><c:out value="${course.title}"/></h1>
  <table width="100%" cellspacing="0" border="1">
    <tr>
      <th>Teacher</th>
      <th>Description</th>
      <c:if test="${isCourseListener}">
        <th>Mark</th>
      </c:if>
    </tr>
    <tr>
      <td><c:out value="${course.teacher.firstName} "/>
        <c:out value="${course.teacher.lastName}"/></td>
      <td><c:out value="${course.description}"/></td>
      <c:if test="${risCourseListener}">
        <td>
          <c:if test="${mark.value != null}">
            <c:out value="${requestScope.mark.value}"/>
          </c:if>
        </td>
      </c:if>
    </tr>
  </table>
<c:if test="${!requestScope.isCourseListener}">
  <s:url value="/students/register/${course.id}" var="registerForTheCourse"/>
  <a href="${registerForTheCourse}">Register for the course</a>
</c:if>
</body>
</html>
