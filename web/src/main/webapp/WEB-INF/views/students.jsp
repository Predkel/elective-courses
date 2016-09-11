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
<div class="course-table" hidden>
    <table border="1" cellspacing="0">
        <tr>
            <th class="title"></th>
        </tr>
        <tr>
            <th>Teacher</th>
            <th>Description</th>
        </tr>
        <tr>
            <td class="teacher"></td>
            <td class="description"></td>
        </tr>
    </table>
    <button>Register</button>
</div>
<script src="../js/jquery-3.1.0.js"></script>
<script src="../js/pagination.js"></script>
<script src="../js/commons.js"></script>
<script>
    establishServerSideErrorsHandler();

    var currentStudent;

    $.get('/students/current', undefined, function (response) {
        currentStudent = response;
        $(document).trigger('studentLoaded')
    }, 'json');

    $(document).on('studentLoaded', function () {
        doPagination('/courses/count', '/courses', 'firstResult', 'maxResults', function (response) {
            var $courses = $('#courses');
            $courses.text('');
            $.each(response, function (index, course) {
                appendCourseLink($courses, course, function (event) {
                        event.preventDefault();
                        var courseId = event.target.id.toString();
                        $.get('/marks', {courseId : courseId, studentId : currentStudent.id}, function (response) {
                            var containerTemplateClass;
                            if ($.isEmptyObject(response)) {
                                containerTemplateClass = 'course-table';
                            } else {
                                containerTemplateClass = 'mark-table';
                            }

                            var table = getTable(containerTemplateClass, courseId);
                            $(event.target).parent().html(table);
                            setCommonFields(course, courseId);

                            if (!$.isEmptyObject(response)) {
                                $('.mark', '.' + courseId).text(response.value);
                            } else {
                                $('.' + courseId).next('button').click(function (event) {
                                    event.stopPropagation();
                                    var newMark = {
                                        course : {id : parseInt(courseId)},
                                        student : currentStudent
                                    };
                                    $.ajax('/marks',
                                            {
                                                method : 'POST',
                                                data : JSON.stringify(newMark),
                                                success : function () {
                                                    $(event.target).parent().html(getTable('mark-table', courseId));
                                                    setCommonFields(course, courseId)
                                                }
                                                ,
                                                contentType : 'application/json; charset=UTF-8'
                                            })
                                })
                            }
                        }, 'json')
                });
            })
        });
    });

    function setCommonFields(course, tableClass) {
        $('.title', '.' + tableClass).text(course.title);
        $('.teacher', '.' + tableClass).text(course.teacher.firstName + ' ' + course.teacher.lastName);
        $('.description','.' + tableClass).text(course.description);
    }
</script>
</body>
</html>
