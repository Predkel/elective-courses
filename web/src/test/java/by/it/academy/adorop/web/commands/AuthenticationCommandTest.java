package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.RequestParamValidator;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.it.academy.adorop.web.commands.AuthenticationCommand.AUTHENTICATION_PAGE;
import static by.it.academy.adorop.web.commands.AuthenticationCommand.FAILED_ATTEMPT_MESSAGE;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestParamValidator.class, CommandsFactory.class})
public abstract class AuthenticationCommandTest<T extends User> {

    private static final String DOCUMENT_ID = "documentId";
    private static final String PASSWORD = "password";
    private AuthenticationCommand command;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    HttpSession session;
    @Mock
    UserService<T> userService;
    @Mock
    ErrorCommand errorCommand;
    private T user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        command = new AuthenticationCommand<>(userService);
        PowerMockito.mockStatic(RequestParamValidator.class, CommandsFactory.class);
        user = getConcreteUserInstance();
    }

    @Test
    public void shouldForwardToAuthenticationPageWhenDocumentIdOrPasswordAreNullOrEmpty() throws ServletException, IOException {
        whenAreEmptyThenReturn(true);
        setupReturningRequestDispatcher();
        command.execute(request, response);
        verifyForwardToAuthenticationPage();
    }

    @Test
    public void shouldForwardToAuthenticationPageWhenStudentIsNotValid() throws ServletException, IOException, ServiceException {
        whenCombinationOfDocumentIdAndPasswordIsNotValid();
        command.execute(request, response);
        verifyForwardToAuthenticationPage();
    }

    @Test
    public void shouldPutMessageIntoRequestWhenCombinationOfDocumentIdAndPasswordIsNotValid() throws ServletException, IOException, ServiceException {
        whenCombinationOfDocumentIdAndPasswordIsNotValid();
        command.execute(request, response);
        verify(request).setAttribute("failedAttemptMessage", FAILED_ATTEMPT_MESSAGE);
    }

    @Test
    public void shouldRetrieveUserByDocumentIdParameterWhenCombinationOfLoginAndPasswordIsValid() throws IOException, ServiceException {
        when(request.getParameter(DOCUMENT_ID)).thenReturn(DOCUMENT_ID);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD);
        whenCombinationOfDocumentIdAndPasswordIsValid();
        command.execute(request, response);
        verify(userService).getByDocumentId(DOCUMENT_ID);
    }

    @Test
    public void shouldCreateAndExecuteErrorCommandIfServiceExceptionWasThrown() throws ServiceException {
        whenAreEmptyThenReturn(false);
        when(userService.isValid(anyString(), anyString())).thenThrow(new ServiceException(new Exception()));
        PowerMockito.when(CommandsFactory.createErrorCommand()).thenReturn(errorCommand);
        command.execute(request, response);
        PowerMockito.verifyStatic();
        CommandsFactory.createErrorCommand();
        verify(errorCommand).execute(request, response);
    }

    @Test
    public void shouldPutRetrievedUserWithSuitableNameIntoSessionWhenUserIsValid() throws IOException, ServiceException {
        whenCombinationOfDocumentIdAndPasswordIsValid();
        command.execute(request, response);
        verify(session).setAttribute(defineNameOfUserAttributeForSession(), user);
    }

    @Test
    public void shouldRedirectToMainWhenStudentIsValid() throws IOException, ServiceException {
        whenCombinationOfDocumentIdAndPasswordIsValid();
        command.execute(request, response);
        verify(response).sendRedirect(definePathToMainCommand());
    }

    abstract T getConcreteUserInstance();
    abstract String defineServletPath();
    abstract String defineNameOfUserAttributeForSession();
    abstract String definePathToMainCommand();

    private void whenCombinationOfDocumentIdAndPasswordIsValid() throws ServiceException {
        whenAreEmptyThenReturn(false);
        whenIsValidThenReturn(true);
        setupReturningSession();
        when(userService.getByDocumentId(anyString())).thenReturn(user);
        when(request.getServletPath()).thenReturn(defineServletPath());
    }

    private void setupReturningSession() {
        when(request.getSession()).thenReturn(session);
    }

    private void setupReturningRequestDispatcher() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    private void whenAreEmptyThenReturn(boolean value) {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(value);
    }

    private void whenCombinationOfDocumentIdAndPasswordIsNotValid() throws ServiceException {
        whenAreEmptyThenReturn(false);
        whenIsValidThenReturn(false);
        setupReturningRequestDispatcher();
    }

    private void whenIsValidThenReturn(boolean value) throws ServiceException {
        when(userService.isValid(anyString(), anyString())).thenReturn(value);
    }

    private void verifyForwardToAuthenticationPage() throws ServletException, IOException {
        verify(request).getRequestDispatcher(AUTHENTICATION_PAGE);
        verify(requestDispatcher).forward(request, response);
    }
}
