package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ShowCourseForTeacherCommand extends BasicShowCourseCommand {


    public static final String PATH_TO_CONTROLLER = "/teachers";

    public ShowCourseForTeacherCommand(HttpServletRequest request) {
        super(request);
    }

    ShowCourseForTeacherCommand(HttpServletRequest request, MarkService markService, CourseService courseService) {
        super(request, markService, courseService);
    }

    @Override
    protected boolean requestIsValid() throws ServiceException {
        return RequestParamValidator.isValidId(courseIdParameter, courseService)
                && CourseSecurity.isTeacherOfTheCourse(getCurrentTeacher(), getCourseById());
    }

    private Course getCourseById() throws ServiceException {
        return courseService.find(Long.valueOf(courseIdParameter));
    }

    private Teacher getCurrentTeacher() {
        return (Teacher) request.getSession().getAttribute("teacher");
    }

    @Override
    protected void setContent() throws ServiceException {
        request.setAttribute("pathToTeachersController", PATH_TO_CONTROLLER);
        request.setAttribute("marks", markService.getByCourse(getCourseById()));
    }

    @Override
    protected void move(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(Constants.COURSE_FOR_TEACHER_PAGE, request, response);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(ShowCourseForTeacherCommand.class);
    }
}
