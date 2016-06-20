package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
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
//
        setPathToProcessLinks();
    }

    private void setCourses(int firstResult, int maxResult) throws ServiceException {
        List<Course> courses = courseService.getBunch(firstResult, maxResult);
        request.setAttribute("courses", courses);
    }

    private void setPathToProcessLinks() {
        request.setAttribute("pathToProcessPagination", PathBuilder.buildPath(request, OPERATION_MAIN));
        request.setAttribute("pathToProcessCourseLink", PathBuilder.buildPath(request, OPERATION_SHOW_COURSE));
    }

    private void setRange(int firstResult, int maxResult, Long totalCount) {
        request.setAttribute("isTheFirstPage", firstResult == 0);
        request.setAttribute("isTheLastPage", firstResult + maxResult >= totalCount);
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
