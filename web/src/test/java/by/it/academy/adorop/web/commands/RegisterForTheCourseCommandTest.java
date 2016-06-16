package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;
import by.it.academy.adorop.web.utils.PathBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.it.academy.adorop.web.commands.Command.OPERATION_SHOW_COURSE;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Dispatcher.class, PathBuilder.class})
public class RegisterForTheCourseCommandTest {

    private static final String PATH = "path";
    private static final String VALID_ID = "17";

    private Command command;

    @Mock
    private IdValidator<Course> idValidator;
    @Mock
    private StudentService studentService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    private final Course course = new Course();
    private final Student student = new Student();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Dispatcher.class, PathBuilder.class);
        command = new RegisterForTheCourseCommand(idValidator, studentService);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(student);
        when(idValidator.getValidModel()).thenReturn(course);
    }

    @Test
    public void shouldForwardToMainWhenCourseIdIsNotValid() throws ServiceException, ServletException, IOException {
        when(idValidator.isValid(anyString())).thenReturn(false);
        command.execute(request, response);
        verify(idValidator).isValid(anyString());
        PowerMockito.verifyStatic();
        Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
    }

    @Test
    public void shouldRegisterStudentForTheCourseWhenCourseIdIsValid() throws ServiceException {
        when(idValidator.isValid(anyString())).thenReturn(true);
        command.execute(request, response);
        verify(studentService).registerForTheCourse(student, course);
    }

    @Test
    public void shouldRedirectToShowCourseWhenCourseIdIsValid() throws ServiceException, IOException {
        when(request.getParameter(anyString())).thenReturn(VALID_ID);
        when(idValidator.isValid(anyString())).thenReturn(true);
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(), anyString(), anyString())).thenReturn(PATH);
        command.execute(request, response);
        verify(idValidator).isValid(anyString());
        verify(response).sendRedirect(PATH);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, OPERATION_SHOW_COURSE, "courseId", VALID_ID);
    }
}