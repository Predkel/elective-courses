package by.it.academy.adorop.web.commands;


import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BasicShowCourseCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(BasicShowCourseCommand.class);

    private final IdValidator<Course> idValidator;
    final MarkService markService;

    BasicShowCourseCommand(MarkService markService, IdValidator<Course> idValidator) {
        this.markService = markService;
        this.idValidator = idValidator;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String courseIdParam = request.getParameter("courseId");
            if (!idValidator.isValid(courseIdParam)) {
                Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
            } else {
                Course course = idValidator.getValidModel();
                setContent(request, course);
                forward(request, response, course);
            }
        } catch (ServletException | IOException | ServiceException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    abstract void setContent(HttpServletRequest request, Course course) throws ServiceException;

    abstract void forward(HttpServletRequest request, HttpServletResponse response, Course course) throws ServletException, IOException;

}