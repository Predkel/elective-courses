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

public abstract class BasicShowCourseCommand extends BasicCommandVerifyingRequest {

    private static final Logger LOGGER = Logger.getLogger(BasicShowCourseCommand.class);

    private final IdValidator<Course> idValidator;
    final MarkService markService;

    public BasicShowCourseCommand(HttpServletRequest request, IdValidator<Course> idValidator, MarkService markService) {
        super(request);
        this.idValidator = idValidator;
        this.markService = markService;
    }
}