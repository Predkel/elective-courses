package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.CourseSecurity;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ShowCourseForTeacherCommand extends BasicShowCourseCommand {

    ShowCourseForTeacherCommand(MarkService markService, IdValidator<Course> idValidator) {
        super(markService, idValidator);
    }

    @Override
    void setContent(HttpServletRequest request, Course course) throws ServiceException {
        if (CourseSecurity.isTeacherOfTheCourse(getTeacher(request), course)) {
            request.setAttribute("marks", markService.getByCourse(course));
            setPathToProcessEvaluate(request);
        }
    }

    @Override
    void forward(HttpServletRequest request, HttpServletResponse response, Course course) throws ServletException, IOException {
        if (!CourseSecurity.isTeacherOfTheCourse(getTeacher(request), course)) {
            Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
        } else {
            Dispatcher.forward(COURSE_FOR_TEACHER_PAGE, request, response);
        }
    }

    private void setPathToProcessEvaluate(HttpServletRequest request) {
        request.setAttribute("action", request.getServletPath());
    }

    private Teacher getTeacher(HttpServletRequest request) {
        return (Teacher) request.getSession().getAttribute("teacher");
    }
}
