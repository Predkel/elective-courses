package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.CourseServiceImpl;
import by.it.academy.adorop.service.implementations.StudentServiceImpl;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.*;

public class RegisterForTheCourseCommand extends BasicCommandVerifyingRequest {

    private final CourseService courseService;
    private final StudentService studentService;
    private final String courseIdParameter;

    public RegisterForTheCourseCommand(HttpServletRequest request) {
        this(request, CourseServiceImpl.getInstance(), StudentServiceImpl.getInstance());
    }

    RegisterForTheCourseCommand(HttpServletRequest request, CourseService courseService, StudentService studentService) {
        super(request);
        this.courseService = courseService;
        this.studentService = studentService;
        courseIdParameter = request.getParameter("courseId");
    }


    @Override
    protected boolean requestIsValid() throws ServiceException {
        return RequestParamValidator.isValidId(courseIdParameter, courseService) &&
                !studentService.isCourseListener(getCurrentStudent(), getRequestedCourse());
    }

    private Student getCurrentStudent() {
        return (Student) request.getSession().getAttribute("student");
    }

    private Course getRequestedCourse() throws ServiceException {
        return courseService.find(Long.valueOf(courseIdParameter));
    }

    @Override
    protected void setExplainingMessage() {
        request.setAttribute("message", REGISTER_FOR_THE_COURSE_MESSAGE);
    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException {
        String pathToMain = PathBuilder.buildPath(request, OPERATION_MAIN);
        Dispatcher.forward(pathToMain, request, response);
    }

    @Override
    protected void prepareResponse() throws ServiceException {
        studentService.registerForTheCourse(getCurrentStudent(), getRequestedCourse());
    }

    @Override
    protected void move(HttpServletResponse response) throws IOException {
        String pathToShowTheSameCourse = PathBuilder.buildPath(request, OPERATION_SHOW_COURSE, "courseId", courseIdParameter);
        response.sendRedirect(pathToShowTheSameCourse);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(RegisterForTheCourseCommand.class);
    }
}
