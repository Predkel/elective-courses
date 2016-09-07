<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
<div id="pagination-container"></div>
<div id="courses"></div>
<div class="mark-table" hidden>
    <table>
        <tr>
            <th id="title"></th>
        </tr>
        <tr>
            <th>Teacher</th>
            <th>Description</th>
            <th>Mark</th>
        </tr>
        <tr>
            <td id="teacher"></td>
            <td id="description"></td>
            <td id="mark"></td>
        </tr>
    </table>
</div>
<script src="../js/jquery-3.1.0.js"></script>
<script src="../js/pagination.js"></script>
<script>
    var currentStudent;
    $.get('/students/current', undefined, function (response) {
        currentStudent = response;
        $(document).trigger('studentLoaded')
    }, 'application/json;charset=UTF-8');
    $(document).on('studentLoaded', function () {
        doPagination('/courses/count', '/courses', 'firstResult', 'maxResults', function (response) {
            var $courses = $('#courses');
                $courses.text('');
                $.each(response, function (index, element) {
                    $courses.append('<a href="#" class="course" id="' + element.id + '">' + element.title + ' </a><br>')
                            .children('.course').click(function (event) {
                        event.preventDefault();
                        var courseId = event.target.id;
                        $.get('/marks', {courseId : courseId, studentId : currentStudent.id}, function (response) {
                            $('#markTable')
                                    .child('#teacher').text(element.teacher.firstName + ' ' + element.teacher.lastName)
                                    .child('#description').text(element.description)
                                    .child('#mark').text(response.value)
                                    .appendTo('.course#' + courseId)
                                    .removeAttr('hidden')
                        }, 'application/json;charset=UTF-8')
                    })
                })
        });
    })
</script>
</body>
</html>
