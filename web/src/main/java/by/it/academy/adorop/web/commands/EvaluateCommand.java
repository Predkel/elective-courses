package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.CourseServiceImpl;
import by.it.academy.adorop.service.implementations.MarkServiceImpl;
import by.it.academy.adorop.service.implementations.TeacherServiceImpl;
import by.it.academy.adorop.web.utils.CourseSecurity;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.*;

public class EvaluateCommand extends BasicCommandVerifyingRequest {

    private final TeacherService teacherService;
    private final CourseService courseService;
    private final MarkService markService;

    private final String courseIdParameter;
    private final String markIdParameter;
    private final String markValueParameter;

    EvaluateCommand(HttpServletRequest request) {
        this(request, TeacherServiceImpl.getInstance(), CourseServiceImpl.getInstance(), MarkServiceImpl.getInstance());
    }

    EvaluateCommand(HttpServletRequest request, TeacherService teacherService, CourseService courseService, MarkService markService) {
        super(request);
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.markService = markService;
        courseIdParameter = request.getParameter("courseId");
        markIdParameter = request.getParameter("markId");
        markValueParameter = request.getParameter("markValue");
    }

    @Override
    protected boolean requestIsValid() throws ServiceException {
        return RequestParamValidator.isValidId(courseIdParameter, courseService)
                && RequestParamValidator.isValidId(markIdParameter, markService)
                && RequestParamValidator.isNumberBetweenZeroAndTen(markValueParameter)
                && CourseSecurity.isTeacherOfTheCourse(getCurrentTeacher(), getRequestedCourse());
    }

    private Teacher getCurrentTeacher() {
        return (Teacher) request.getSession().getAttribute("teacher");
    }

    private Course getRequestedCourse() throws ServiceException {
        return courseService.find(Long.valueOf(courseIdParameter));
    }

    @Override
    protected void setExplainingMessage() {
        String message = RequestParamValidator.isNumberBetweenZeroAndTen(markValueParameter) ? FAILED_EVALUATE_MESSAGE
                : MARK_VALUE_MESSAGE;
        request.setAttribute("message", message);
    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException {
        if (!RequestParamValidator.isNumberBetweenZeroAndTen(markValueParameter)) {
            String pathToTheSamePage = buildPathToTheSamePage(request, courseIdParameter);
            Dispatcher.forward(pathToTheSamePage, request, response);
        } else {
            Dispatcher.forwardToMain(request, response);
        }
    }

    @Override
    protected void prepareResponse() throws ServiceException {
        Mark mark = markService.find(Long.valueOf(markIdParameter));
        mark.setValue(Integer.valueOf(markValueParameter));
        teacherService.evaluate(mark);
    }

    @Override
    protected void move(HttpServletResponse response) throws IOException {
        String pathToTheSamePage = buildPathToTheSamePage(request, courseIdParameter);
        response.sendRedirect(pathToTheSamePage);
    }

    private String buildPathToTheSamePage(HttpServletRequest request, String courseIdParameter) {
        return PathBuilder.buildPath(request, OPERATION_SHOW_COURSE, "courseId", courseIdParameter);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(EvaluateCommand.class);
    }
}
