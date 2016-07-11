<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><s:message code="courses"/> </title>
</head>
<body>
<h1><s:message code="courses"/></h1><br>
${message}
<sec:authentication property="principal" var="currentTeacher"/>
<ul>
    <c:forEach items="${courses}" var="course">
        <li>
            <c:choose>
                <c:when test="${course.teacher.equals(currentTeacher)}">
                    <s:url value="/teachers/course?courseId=${course.id}" var="showCourse"/>
                    <a href="${showCourse}">
                        <c:out value="${course.title}"/>
                    </a>
                </c:when>
                <c:otherwise><c:out value="${course.title}"/></c:otherwise>
            </c:choose>
        </li>
    </c:forEach>
</ul>
<a href="<c:url value="/teachers/add"/>"><s:message code="course.add"/> </a><br>
</body>
</html>
