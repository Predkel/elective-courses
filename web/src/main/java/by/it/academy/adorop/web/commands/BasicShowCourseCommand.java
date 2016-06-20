package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.CourseServiceImpl;
import by.it.academy.adorop.service.implementations.MarkServiceImpl;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BasicShowCourseCommand extends BasicCommandVerifyingRequest {
    final MarkService markService;
    final CourseService courseService;
    final String courseIdParameter;

    BasicShowCourseCommand(HttpServletRequest request) {
        this(request, MarkServiceImpl.getInstance(), CourseServiceImpl.getInstance());
    }

    BasicShowCourseCommand(HttpServletRequest request, MarkService markService, CourseService courseService) {
        super(request);
        this.markService = markService;
        this.courseService = courseService;
        courseIdParameter = request.getParameter("courseId");
    }

    @Override
    protected void setExplainingMessage() {
        request.setAttribute("message", Constants.FOLLOW_THE_LINK_MESSAGE);
    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException {
        String pathToMain = PathBuilder.buildPath(request, Constants.OPERATION_MAIN);
        Dispatcher.forward(pathToMain, request, response);
    }
}