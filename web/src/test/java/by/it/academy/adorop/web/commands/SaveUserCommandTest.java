package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.UserService;
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Dispatcher.class, RequestParamValidator.class, PathBuilder.class})
public class SaveUserCommandTest {

    private static final String ANY_PATH = "any path";
    private Command command;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    UserService userService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Dispatcher.class, RequestParamValidator.class, PathBuilder.class);
        command = new SaveUserCommand<>(userService);
    }

    @Test
    public void whenParametersAreEmpty() throws ServletException, IOException {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(true);
        command.execute(request, response);
        shouldForwardToRegisterFormWithRelevantMessage(Command.SHOULD_BE_NOT_EMPTY_MESSAGE);
    }

    @Test
    public void whenUserWithTheSameDocumentIdAlreadyExists() throws ServiceException, ServletException, IOException {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(false);
        when(userService.isAlreadyExists(anyString())).thenReturn(true);
        command.execute(request, response);
        shouldForwardToRegisterFormWithRelevantMessage(Command.USER_ALREADY_EXISTS_MESSAGE);
    }

    @Test
    public void shouldRedirectToMain() throws ServiceException, IOException {
        whenRequestIsValid();
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(ANY_PATH);
        command.execute(request, response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Command.OPERATION_MAIN);
        verify(response).sendRedirect(ANY_PATH);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSaveNewUserAndPutIntoSession() throws Exception {
        whenRequestIsValid();
        Teacher savedTeacher = new Teacher();
        when(userService.save(anyObject())).thenReturn(savedTeacher);
        command.execute(request,response);
        verify(userService).save(anyObject());
        verify(session).setAttribute("teacher", savedTeacher);
    }

    private void whenRequestIsValid() throws ServiceException {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(false);
        when(userService.isAlreadyExists(anyString())).thenReturn(false);
        when(request.getSession()).thenReturn(session);
        when(request.getServletPath()).thenReturn(ANY_PATH);
    }

    private void shouldForwardToRegisterFormWithRelevantMessage(String message) throws ServletException, IOException {
        PowerMockito.verifyStatic();
        Dispatcher.forwardWithMessage(Command.REGISTER_FORM, request, response, message);
    }

}
