package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.it.academy.adorop.web.commands.MainCommand.MAIN_PAGE;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestParamValidator.class, CommandsFactory.class,
        Dispatcher.class, PathBuilder.class})
public class MainCommandTest {

    private static final String PATH_TO_PROCESS_PAGING = "path";
    private static final int TEN = 10;
    private static final String PATH_TO_PROCESS_COURSE_LINK = "path to process course link";

    private MainCommand command;
    @Mock
    private CourseService courseService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    Session session;
    @Mock
    Paginator paginator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RequestParamValidator.class, CommandsFactory.class,
                Dispatcher.class, PathBuilder.class);
        command = new MainCommand(courseService, paginator);
    }

    @Test
    public void shouldSetTotalNumberOfCoursesToPaginator() {
        command.execute(request, response);
        verify(paginator).setTotalNumberOfEntities(anyLong());
    }

    @Test
    public void shouldRetrieveBunchOfCoursesByParametersObtainedFromPaginatorAndPutThemIntoRequest() throws ServiceException {
        List<Course> coursesFromService = createExampleListOfCourses();
        when(paginator.defineFirstResult()).thenReturn(TEN);
        when(paginator.defineMaxResult()).thenReturn(TEN);
        when(courseService.getBunch(anyInt(), anyInt())).thenReturn(coursesFromService);
        command.execute(request, response);
        verify(courseService).getBunch(TEN, TEN);
        verify(request).setAttribute("courses", coursesFromService);
    }

    @Test
    public void shouldForwardToMainPage() throws ServletException, IOException {
        command.execute(request, response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(MAIN_PAGE, request, response);
    }

    @Test
    public void shouldPutPathToProcessPaginationIntoRequest() {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(PATH_TO_PROCESS_PAGING);
        command.execute(request, response);
        verify(request).setAttribute("pathToProcessPagination", PATH_TO_PROCESS_PAGING);
    }

    @Test
    public void shouldPutPathToProcessCourseLinkIntoRequest() {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(PATH_TO_PROCESS_COURSE_LINK);
        command.execute(request, response);
        verify(request).setAttribute("pathToProcessCourseLink", PATH_TO_PROCESS_COURSE_LINK);
    }

    private List<Course> createExampleListOfCourses() {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            courses.add(new Course());
        }
        return courses;
    }
}
