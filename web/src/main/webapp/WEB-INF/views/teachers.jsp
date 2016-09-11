<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teachers</title>
</head>
<body>
<div id="add-course-container">
    <a href="#" id="add-course-link">Add Course</a>
</div>
<div class="add-course-form" hidden>
    <form class="add-course-form">
        Title : <label>
        <input class="title" name="title">
    </label>
        <span class="error-title-span"></span>
        Description: <label>
        <textarea class="description"></textarea>
    </label>
        <input type="submit" value="Save">
    </form>
</div>
<div id="pagination-container"></div>
<div id="courses"></div>
<div class="course-table" hidden>
    <table border="1" cellspacing="0">
        <tr class="title"></tr>
        <tr>
            <th>Student</th>
            <th>Mark</th>
        </tr>
    </table>
</div>
<table class="course-row" hidden>
    <tr>
        <td class="student"></td>
        <td class="mark"></td>
    </tr>
</table>
<div class="evaluate-form" hidden>
    <div>
        <input name="value">
        <button>Evaluate</button><br>
        <span class="mark-value-error"></span>
    </div>
</div>
<script src="../js/jquery-3.1.0.js"></script>
<script src="../js/pagination.js"></script>
<script src="../js/commons.js"></script>
<script>
    var currentTeacher;

    $.get('/teachers/current', undefined, function (response) {
        currentTeacher = response;
        $(document).trigger('teacherLoaded');
    }, 'json');

    $(document).on('teacherLoaded', function () {
        $('#add-course-container').one('click', function (event) {
            onAddCourseLinkClick(event);

            function onAddCourseLinkClick(event) {
                event.preventDefault();
                var addCourseForm = $('div.add-course-form[hidden]')
                        .clone()
                        .removeAttr('hidden')
                        .html();
                $('#add-course-container').html(addCourseForm);
                $('form.add-course-form', '#add-course-container')
                        .submit(function (event) {
                            event.preventDefault();
                            event.stopPropagation();
                            var $errorSpan = $('.error-title-span', '#add-course-container');
                            $errorSpan.text('');
                            var $form = $(this);
                            var title = $form.find('.title').val();
                            if ($.trim(title) == '') {
                                $errorSpan.text('Should be not empty');
                            } else {
                                var description = $form.find('.description').val();
                                var newCourse = {
                                    title : title,
                                    description : description,
                                    teacher : currentTeacher
                                };
                                $.ajax('/courses', {
                                    method : 'POST',
                                    contentType : 'application/json; charset=UTF-8',
                                    data : JSON.stringify(newCourse),
                                    success : function () {
                                        var $addCourseContainer = $('#add-course-container');
                                        $addCourseContainer.html('<a href="#" id="add-course-link">Add course</a>');
                                        $addCourseContainer.one('click', function (event) {
                                            onAddCourseLinkClick(event);
                                        })
                                    },
                                    error : function (jqXHR, statusText) {
                                        if (jqXHR.status == 409) {
                                            $errorSpan.text("You already have course with the same title")
                                        }
                                    }
                                })
                            }
                        })
            }

        });

        doPagination('/courses/count', '/courses', 'firstResult', 'maxResults', function (response) {
            onCoursesLoaded(response);

            function onCoursesLoaded(response) {
                var $coursesContainer = getEmptyCoursesContainer();
                setCourses(response, $coursesContainer);

                function getEmptyCoursesContainer() {
                    var $courses = $('#courses');
                    $courses.text('');
                    return $courses;
                }

                function setCourses(courses, $coursesContainer) {
                    $.each(courses, function (index, course) {
                        if (course.teacher.id == currentTeacher.id) {
                            appendCourseLink($coursesContainer, course, function (event) {
                                onCourseLinkClick(event, course);
                            })
                        } else {
                            $coursesContainer.append('<div>' + course.title + '</div>')
                        }

                        function onCourseLinkClick(event, course) {
                            event.preventDefault();
                            var courseId = event.target.id.toString();
                            $.get('/marks',
                                    'courseId=' + courseId,
                                    function (marks) {
                                        setCourseTableTemplate(courseId, event, course);
                                        setCourseTableContent(marks, courseId)
                                    }, 'json')
                        }

                        function setCourseTableTemplate(tableClass, event, course) {
                            var table = getTable('course-table', tableClass);
                            $(event.target).parent().html(table);
                            $('.title', '.' + tableClass).text(course.title);
                        }

                        function setCourseTableContent(marks, tableClass) {
                            $.each(marks, function (index, mark) {
                                var markId = mark.id.toString();
                                appendCourseRowTemplate(markId, tableClass);
                                fillStudentCell(markId, mark);
                                var markValue = mark.value;
                                if (markValue == null || markValue == undefined) {
                                    insertEvaluateForm(markId, mark);
                                } else {
                                    $('.mark', '.' + markId).text(markValue)
                                }

                                function appendCourseRowTemplate(rowClass, tableClass) {
                                    var $courseRowTemplate = getCourseRowTemplate(rowClass);
                                    var courseRowTemplate = $courseRowTemplate.html();
                                    $('table.' + tableClass).append(courseRowTemplate);

                                    function getCourseRowTemplate(rowClass) {
                                        var $courseRow = $('.course-row[hidden]').clone();
                                        $courseRow.find('tr').addClass(rowClass);
                                        $courseRow.removeAttr('hidden');
                                        return $courseRow;
                                    }
                                }

                                function fillStudentCell(rowClass, mark) {
                                    $('.student', '.' + rowClass).text(mark.student.firstName + ' ' + mark.student.lastName);
                                }

                                function insertEvaluateForm(markId, mark) {
                                    var $evaluateForm = $('.evaluate-form[hidden]').clone();

                                    var evaluateForm = $evaluateForm.removeAttr('hidden').html();
                                    $('.mark', "." + markId).html(evaluateForm);

                                    $('button', '.' + markId).on('click', function (event) {
                                        onEvaluateFormSubmit(event, mark, markId)
                                    });

                                    function onEvaluateFormSubmit(event, mark, markId) {
                                        event.preventDefault();
                                        event.stopPropagation();
                                        var $errorSpan = $('span.mark-value-error', '.' + markId);
                                        $errorSpan.text('');
                                        var markValue = getMarkValue(event);
                                        if (isNaN(markValue) || markValue > 10 || markValue < 0) {
                                            $errorSpan.text('Should be a number between zero(0) and ten(10)')
                                        } else {
                                            $('button', '.' + markId).off('click');
                                            ajaxToUpdate(mark, markValue);
                                        }

                                        function getMarkValue(event) {
                                            return $(event.target).siblings('input[name=value]').val();
                                        }

                                        function ajaxToUpdate(mark, markValue) {
                                            var markWithValue = {
                                                id : mark.id,
                                                course : mark.course,
                                                student : mark.student,
                                                value : markValue
                                            };
                                            $.ajax('/marks',{
                                                method : 'PUT',
                                                data : JSON.stringify(markWithValue),
                                                contentType : 'application/json; charset=UTF-8',
                                                success : function () {
                                                    $('.mark', '.' + markId).text(markValue)
                                                }
                                            })
                                        }
                                    }
                                }
                            });

                        }
                    });
                }
            }
        })
    })
</script>
</body>
</html>
