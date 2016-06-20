package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaveUserCommandTest extends BasicCommandVerifyingRequestTest {

    private static final String STUDENTS_SERVLET_PATH = "/students";
    @Mock
    UserService userService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        command = new SaveUserCommand(request, userService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenAtLeastOneOfParametersIsEmpty() throws Exception {
        PowerMockito.when(RequestParamValidator.hasEmpty(anyVararg())).thenReturn(true);
        assertFalse(command.requestIsValid());
    }
    @Test
    public void requestIsValidShouldReturnFalseWhenUserWithTheSameDocumentIdAlreadyExists() throws Exception {
        PowerMockito.when(RequestParamValidator.hasEmpty(anyVararg())).thenReturn(false);
        when(userService.isAlreadyExists(anyString())).thenReturn(true);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void testRequestIsValidOnPositiveScenario() throws Exception {
        PowerMockito.when(RequestParamValidator.hasEmpty(anyVararg())).thenReturn(false);
        when(userService.isAlreadyExists(anyString())).thenReturn(false);
        assertTrue(command.requestIsValid());
    }

    @Test
    public void setExplainingMessageShouldSetFieldsShouldBeNotEmptyMessageWhenOneOfParameterIsEmpty() throws Exception {
        PowerMockito.when(RequestParamValidator.hasEmpty(anyVararg())).thenReturn(true);
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.FIELDS_SHOULD_BE_NOT_EMPTY_MESSAGE);
    }
    @Test
    public void setExplainingMessageShouldSetUserAlreadyExistsMessageWhenUserWithGivenDocumentIdAlreadyExists() throws Exception {
        PowerMockito.when(RequestParamValidator.hasEmpty(anyVararg())).thenReturn(false);
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.USER_ALREADY_EXISTS_MESSAGE);
    }

    @Test
    public void sendToRelevantPageShouldForwardToRegistrationPage() throws Exception {
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.REGISTRATION_PAGE, request, response);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void prepareResponseShouldSaveStudentAndPutIntoSessionWithRelevantNameWhenRequestIsFromStudent() throws Exception {
        Student student = whenRequestIsFromStudent();
        command.prepareResponse();
        verify(session).setAttribute("student", student);
        verify(userService).persist(anyObject());
    }

    @SuppressWarnings("unchecked")
    private Student whenRequestIsFromStudent() throws ServiceException {
        when(request.getServletPath()).thenReturn(STUDENTS_SERVLET_PATH);
        Student student = new Student();
        when(userService.persist(anyObject())).thenReturn(student);
        return student;
    }

    @Test
    public void moveShouldRedirectToMain() throws Exception {
        String pathToMain = "path to main";
        PowerMockito.when(PathBuilder.buildPathToMain(anyObject())).thenReturn(pathToMain);
        command.move(response);
        verify(response).sendRedirect(pathToMain);
    }
}