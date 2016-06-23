package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import by.it.academy.adorop.web.utils.pagination.PaginatorBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.it.academy.adorop.web.utils.Constants.*;

public class MainCommand extends BasicCommand {

    private final CourseService courseService;

    public MainCommand(HttpServletRequest request, CourseService courseService) {
        super(request);
        this.courseService = courseService;
    }

    @Override
    protected void prepareResponse() throws ServiceException {
        Long totalCount = courseService.getTotalCount();
        setupPagination(totalCount);
        setPathToProcessLinks();
    }

    private void setupPagination(Long totalCount) throws ServiceException {
        PaginatorBuilder paginatorBuilder = new PaginatorBuilder(request);
        Paginator paginator = paginatorBuilder.buildPaginator();
        setCourses(paginator);
        paginator.setPagesNumbersIntoRequest(totalCount);
    }

    private void setCourses(Paginator paginator) throws ServiceException {
        List<Course> courses = courseService.getBunch(paginator.getFirstResult(), paginator.getMaxResult());
        request.setAttribute("courses", courses);
    }

    private void setPathToProcessLinks() {
        request.setAttribute("pathToMain", PathBuilder.buildPathToMain(request));
        request.setAttribute("pathToProcessCourseLink", PathBuilder.buildPath(request, OPERATION_SHOW_COURSE));
    }

    @Override
    protected void move(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(MAIN_PAGE, request, response);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(MainCommand.class);
    }
}
