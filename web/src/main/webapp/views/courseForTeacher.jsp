<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><c:out value="${requestScope.course.title}"/></title>
</head>
<body>
  <h1><c:out value="${requestScope.course.title}"/></h1>
  ${message}
  <table border="0" width="50%">
    <c:forEach var="mark" items="${requestScope.marks}">
      <tr>
        <td><c:out value="${mark.student.firstName} ${mark.student.lastName}"/></td>
        <td>
          <c:choose>
            <c:when test="${mark.value == null}">
              <form action="${requestScope.action}" method="post">
                <input type="text" name="markValue">
                <input type="hidden" name="markId" value="${mark.id}">
                <input type="hidden" name="courseId" value="${mark.course.id}">
                <input type="hidden" name="operation" value="evaluate">
                <input type="submit" value="Evaluate">
              </form>
            </c:when>
            <c:otherwise><c:out value="${mark.value}"/></c:otherwise>
          </c:choose>
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
