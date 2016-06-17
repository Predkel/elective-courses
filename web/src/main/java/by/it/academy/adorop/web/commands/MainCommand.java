package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(MainCommand.class);

    private final CourseService courseService;
    private final Paginator paginator;

    public MainCommand(HttpServletRequest request, CourseService courseService, Paginator paginator) {
        super(request);
        this.courseService = courseService;
        this.paginator = paginator;
    }

    @Override
    protected boolean requestIsValid() throws ServiceException, IOException, ServletException {
        return false;
    }

    @Override
    protected void setContent() throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void goFurther(HttpServletResponse response) throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void setExplainingMessage() throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServiceException, IOException, ServletException {

    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
