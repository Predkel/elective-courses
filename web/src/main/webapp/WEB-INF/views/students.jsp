<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
<div id="pagination-container"></div>
<div id="courses"></div>
<div class="mark-table" hidden>
    <table border="1" cellspacing="0">
        <tr>
            <th class="title"></th>
        </tr>
        <tr>
            <th>Teacher</th>
            <th>Description</th>
            <th>Mark</th>
        </tr>
        <tr>
            <td class="teacher"></td>
            <td class="description"></td>
            <td class="mark"></td>
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
    }, 'json');

    $(document).on('studentLoaded', function () {
        doPagination('/courses/count', '/courses', 'firstResult', 'maxResults', function (response) {
            var $courses = $('#courses');
            $courses.text('');
            $.each(response, function (index, element) {
                var courseLink = '<div><a href="#" class="course" id="' + element.id + '">'  + element.title + ' </a><br></div>';
                var $courseLink = $(courseLink);
                $courseLink.click(function (event) {
                        event.preventDefault();
                        var courseId = event.target.id;
                        $.get('/marks', {courseId : courseId, studentId : currentStudent.id}, function (response) {
                            var $markTable = $('.mark-table[hidden]').clone();
                            $markTable.find('table').addClass(courseId.toString());
                            var table = $markTable
                                    .removeAttr('hidden').html();
                            $(event.target).parent().html(table);
                            $('.title', '.' + courseId).text(element.title);
                            $('.teacher', '.' + courseId).text(element.teacher.firstName + ' ' + element.teacher.lastName);
                            $('.description','.' + courseId).text(element.description);
                            $('.mark', '.' + courseId).text(response.value);
                        }, 'json')
                    });
                $courses.append($courseLink);
            })
        });
    })
</script>
</body>
</html>
