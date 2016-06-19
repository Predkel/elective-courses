package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.TeacherServiceImpl;
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

public class SaveCourseCommand extends BasicCommandVerifyingRequest {

    private final TeacherService teacherService;
    private final String titleParameter;
    private final String descriptionParameter;

    SaveCourseCommand(HttpServletRequest request) {
        this(request, TeacherServiceImpl.getInstance());
    }

    SaveCourseCommand(HttpServletRequest request, TeacherService teacherService) {
        super(request);
        this.teacherService = teacherService;
        titleParameter = request.getParameter("title");
        descriptionParameter = request.getParameter("description");
    }


    @Override
    protected boolean requestIsValid() {
        return !RequestParamValidator.isEmpty(titleParameter);
    }

    @Override
    protected void setExplainingMessage() {
        request.setAttribute("message", SHOULD_BE_NOT_EMPTY_MESSAGE);
    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException {
        String pathToAddCourse = PathBuilder.buildPath(request, OPERATION_ADD_COURSE);
        Dispatcher.forward(pathToAddCourse, request, response);
    }

    @Override
    protected void prepareResponse() throws ServiceException {
        Course course = buildCourseFromRequest();
        teacherService.addCourse(getCurrentTeacher(), course);
    }

    private Course buildCourseFromRequest() {
        Course course = new Course();
        course.setTitle(titleParameter);
        course.setDescription(descriptionParameter);
        course.setTeacher(getCurrentTeacher());
        return course;
    }

    private Teacher getCurrentTeacher() {
        return (Teacher) request.getSession().getAttribute("teacher");
    }

    @Override
    protected void move(HttpServletResponse response) throws IOException {
        String pathToMain = PathBuilder.buildPathToMain(request);
        response.sendRedirect(pathToMain);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(SaveCourseCommand.class);
    }
}
