package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Dispatcher.class, RequestParamValidator.class, PathBuilder.class})
public class SaveCourseCommandTest {

    private static final String PATH_TO_MAIN = "path to main";
    private Command command;
    @Mock
    TeacherService teacherService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Dispatcher.class, RequestParamValidator.class, PathBuilder.class);
        command = new SaveCourseCommand(teacherService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldForwardToAddCourseFormWithRelevantMessageWhenTitleParameterIsEmpty() throws ServletException, IOException {
        PowerMockito.when(RequestParamValidator.isEmpty(anyString())).thenReturn(true);
        command.execute(request, response);
        shouldForwardToAddCourseFormWithRelevantMessage(Command.SHOULD_BE_NOT_EMPTY_MESSAGE);
    }

    @Test
    public void shouldSaveCourseIfRequestIsValid() throws ServiceException {
        whenTitleIsNotEmpty();
        command.execute(request, response);
        verify(teacherService).addCourse(anyObject(), anyObject());
    }

    @Test
    public void whenTheSameCourseAlreadyExists() throws ServiceException, ServletException, IOException {
        whenTitleIsNotEmpty();
        when(teacherService.addCourse(anyObject(), anyObject())).thenReturn(false);
        command.execute(request, response);
        shouldForwardToAddCourseFormWithRelevantMessage(Command.THE_SAME_COURSE_ALREADY_EXISTS_MESSAGE);
    }

    @Test
    public void shouldRedirectToMainWhenAddingTheCourseWasSuccessful() throws ServiceException, IOException {
        whenTitleIsNotEmpty();
        when(teacherService.addCourse(anyObject(), anyObject())).thenReturn(true);
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(PATH_TO_MAIN);
        command.execute(request, response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Command.OPERATION_MAIN);
        verify(response).sendRedirect(PATH_TO_MAIN);
    }

    private void whenTitleIsNotEmpty() {
        PowerMockito.when(RequestParamValidator.isEmpty(anyString())).thenReturn(false);
    }

    private void shouldForwardToAddCourseFormWithRelevantMessage(String message) throws ServletException, IOException {
        PowerMockito.verifyStatic();
        Dispatcher.forwardWithMessage(Command.ADD_COURSE_FORM, request, response, message);
    }
}