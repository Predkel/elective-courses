<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><c:out value="${requestScope.course.title}"/></title>
</head>
<body>
<h1><c:out value="${requestScope.course.title}"/></h1>
  <table width="100%" cellspacing="0" border="1">
    <tr>
      <th>Teacher</th>
      <th>Description</th>
      <c:if test="${requestScope.isCourseListener}">
        <th>Mark</th>
      </c:if>
    </tr>
    <tr>
      <td><c:out value="${requestScope.course.teacher.firstName} "/>
        <c:out value="${requestScope.course.teacher.lastName}"/></td>
      <td><c:out value="${requestScope.course.description}"/></td>
      <c:if test="${requestScope.isCourseListener}">
        <td>
          <c:if test="${requestScope.mark.value != null}">
            <c:out value="${requestScope.mark.value}"/>
          </c:if>
        </td>
      </c:if>
    </tr>
  </table>
<c:if test="${!requestScope.isCourseListener}">
  <a href="${requestScope.action}&courseId=${requestScope.course.id}">Register for the course</a>
</c:if>
</body>
</html>
