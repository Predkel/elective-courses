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

public class MainCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(MainCommand.class);

    private final CourseService courseService;
    private final Paginator paginator;

    public MainCommand(CourseService courseService, Paginator paginator) {
        this.courseService = courseService;
        this.paginator = paginator;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            setContent(request);
            Dispatcher.forward(MAIN_PAGE, request, response);
        } catch (ServletException | IOException | ServiceException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private void setContent(HttpServletRequest request) throws ServiceException {
        setCourses(request);
        request.setAttribute("pathToProcessCourseLink", PathBuilder.buildPath(request, OPERATION_SHOW_COURSE));
        request.setAttribute("pathToProcessPagination", PathBuilder.buildPath(request, OPERATION_MAIN));
    }

    private void setCourses(HttpServletRequest request) throws ServiceException {
        Long totalCount = courseService.getTotalCount();
        paginator.setTotalNumberOfEntities(totalCount);
        int firstResult = paginator.defineFirstResult();
        int maxResult = paginator.defineMaxResult();
        setRange(request, totalCount, firstResult, maxResult);
        request.setAttribute("courses", courseService.getBunch(firstResult, maxResult));
    }

    private void setRange(HttpServletRequest request, Long totalCount, int firstResult, int maxResult) {
        request.setAttribute("isTheFirstPage", firstResult == 0);
        request.setAttribute("isTheLastPage", firstResult + maxResult >= totalCount);
    }
}
