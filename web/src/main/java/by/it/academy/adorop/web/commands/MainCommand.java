package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.it.academy.adorop.web.utils.Constants.*;

public class MainCommand extends Command {

    private final CourseService courseService;
    private final Paginator paginator;

    public MainCommand(HttpServletRequest request, CourseService courseService, Paginator paginator) {
        super(request);
        this.courseService = courseService;
        this.paginator = paginator;
    }

    @Override
    protected boolean requestIsValid() throws ServiceException, IOException, ServletException {
        return true;
    }

    @Override
    protected void setContent() throws ServiceException, IOException, ServletException {
        Long totalCount = courseService.getTotalCount();
        paginator.setTotalNumberOfEntities(totalCount);
        int firstResult = paginator.defineFirstResult();
        int maxResult = paginator.defineMaxResult();
        setCourses(firstResult, maxResult);
        setRange(firstResult, maxResult, totalCount);
        setLinks();
    }

    private void setCourses(int firstResult, int maxResult) throws ServiceException {
        List<Course> courses = courseService.getBunch(firstResult, maxResult);
        request.setAttribute("courses", courses);
    }

    private void setLinks() {
        request.setAttribute("pathToProcessPagination", PathBuilder.buildPath(request, OPERATION_MAIN));
        request.setAttribute("pathToProcessCourseLink", PathBuilder.buildPath(request, OPERATION_SHOW_COURSE));
    }

    private void setRange(int firstResult, int maxResult, Long totalCount) {
        request.setAttribute("isTheFirstPage", firstResult == 0);
        request.setAttribute("isTheLastPage", firstResult + maxResult >= totalCount);
    }

    @Override
    protected void move(HttpServletResponse response) throws ServiceException, IOException, ServletException {
        Dispatcher.forward(MAIN_PAGE, request, response);
    }

    @Override
    protected void setExplainingMessage() throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServiceException, IOException, ServletException {

    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(MainCommand.class);
    }
}
