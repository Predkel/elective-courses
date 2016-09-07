<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
<div id="pagination-container"></div>
<div id="courses"></div>
<script src="../js/jquery-3.1.0.js"></script>
<script src="../js/pagination.js"></script>
<script>
    doPagination('/courses/count', '/courses', 'firstResult', 'maxResults', function (response) {
        var $courses = $('#courses');
            $courses.text('');
            $.each(response, function (index, element) {
                $courses.append('<a href="#" id="' + element.id + '">' + element.title + ' </a><br>')
            })
    });
</script>
</body>
</html>
