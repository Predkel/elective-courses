package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.it.academy.adorop.web.utils.Constants.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainCommandTest extends BasicCommandTest {

    private final int FIRST_RESULT = 1;
    private final long TOTAL_COUNT = 1L;
    private final int MAX_RESULT = 2;
    private static final String PATH_TO_MAIN = "any path";
    private static final String PATH_TO_SHOW_COURSE = "path to show course";
    @Mock
    private CourseService courseService;
    @Mock
    private Paginator paginator;

    @Before
    public void setUp() throws Exception {
        super.setup();
        command = new MainCommand(request, courseService, paginator);
    }

    @Test
    public void requestIsValidShouldReturnTrue() throws Exception {
        assertTrue(command.requestIsValid());
    }

    @Test
    public void setContentShouldGetCountOfCoursesAndPassItToPaginator() throws ServiceException, IOException, ServletException {
        when(courseService.getTotalCount()).thenReturn(TOTAL_COUNT);
        command.setContent();
        verify(courseService).getTotalCount();
        verify(paginator).setTotalNumberOfEntities(TOTAL_COUNT);
    }

    @Test
    public void setContentShouldGetFirstResultAndMaxResultFromPaginatorAndPassItToCourseService() throws Exception {
        when(paginator.defineFirstResult()).thenReturn(FIRST_RESULT);
        when(paginator.defineMaxResult()).thenReturn(MAX_RESULT);
        command.setContent();
        verify(paginator).defineFirstResult();
        verify(paginator).defineMaxResult();
        verify(courseService).getBunch(FIRST_RESULT, MAX_RESULT);
    }

    @Test
    public void setContentShouldPutCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        when(courseService.getBunch(anyInt(), anyInt())).thenReturn(courses);
        command.setContent();
        verify(request).setAttribute("courses", courses);
    }

    @Test
    public void setContentShouldSetRange() throws Exception {
        when(paginator.defineFirstResult()).thenReturn(FIRST_RESULT);
        when(paginator.defineMaxResult()).thenReturn(MAX_RESULT);
        when(courseService.getTotalCount()).thenReturn(TOTAL_COUNT);
        command.setContent();
        verify(request).setAttribute("isTheFirstPage", FIRST_RESULT == 0);
        verify(request).setAttribute("isTheLastPage", FIRST_RESULT + MAX_RESULT >= TOTAL_COUNT);
    }

    @Test
    public void setContentShouldSetPathsToProcessPaginationAndCourseLink() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(request, OPERATION_MAIN)).thenReturn(PATH_TO_MAIN);
        PowerMockito.when(PathBuilder.buildPath(request, OPERATION_SHOW_COURSE)).thenReturn(PATH_TO_SHOW_COURSE);
        command.setContent();
        verify(request).setAttribute("pathToProcessPagination", PATH_TO_MAIN);
        verify(request).setAttribute("pathToProcessCourseLink", PATH_TO_SHOW_COURSE);
    }

    @Test
    public void moveShouldForwardToMainPage() throws Exception {
        command.move(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(MAIN_PAGE, request, response);
    }
}