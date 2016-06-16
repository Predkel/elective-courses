<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
<h1>Courses</h1><br>
${message}
<ul>
    <c:forEach items="${requestScope.courses}" var="course">
        <li>
            <c:choose>
                <c:when test="${course.teacher.equals(sessionScope.teacher)}">
                    <a href="${requestScope.pathToProcessCourseLink}&courseId=${course.id}">
                        <c:out value="${course.title}"/>
                    </a>
                </c:when>
                <c:otherwise><c:out value="${course.title}"/></c:otherwise>
            </c:choose>
        </li>
    </c:forEach>
</ul>
<a href="<c:url value="/teachers?operation=addCourse"/>">Add course</a><br>
</body>
</html>
