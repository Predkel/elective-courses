package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import static by.it.academy.adorop.web.utils.Constants.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationCommandTest extends BasicCommandTest {

    private static final String ANY_STRING = "";
    private final String ANY_PATH = "any path";
    @Mock
    UserService userService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        super.setup();
        command = new AuthenticationCommand<>(request, userService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenOneOfParametersIsEmpty() throws Exception {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(true);
//        buildCommand(ANY_STRING, ANY_STRING);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenUserWithGivenDocumentIdAndPasswordDoesNotExist() throws Exception {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(false);
        when(userService.isValid(anyString(), anyString())).thenReturn(false);
//        buildCommand(ANY_STRING, ANY_STRING);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void requestIsValidShouldReturnTrueWhenUserWithGivenParametersExists() throws Exception {
        PowerMockito.when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(false);
        when(userService.isValid(anyString(), anyString())).thenReturn(true);
//        buildCommand(ANY_STRING, ANY_STRING);
        assertTrue(command.requestIsValid());
    }

    @Test
    public void setContentShouldPutStudentIntoSessionWhenRequestIsFromStudent() throws Exception {
        Student student = new Student();
        when(userService.getByDocumentId(anyString())).thenReturn(student);
        command.setContent();
        verify(session).setAttribute("student", student);
    }

    @Test
    public void setContentShouldPutTeacherIntoSessionWhenRequestIsFromTeacher() throws Exception {
        Teacher teacher = new Teacher();
        when(userService.getByDocumentId(anyString())).thenReturn(teacher);
        command.setContent();
        verify(session).setAttribute("teacher", teacher);
    }

    @Test
    public void goFurtherShouldRedirectToMain() throws Exception {
        when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(ANY_PATH);
        command.goFurther(response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, OPERATION_MAIN);
        verify(response).sendRedirect(ANY_PATH);
    }

    @Test
    public void setExplainingMessageShouldSetRelevantMessageIfParametersAreEmpty() throws Exception {
        when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(true);
        command.setExplainingMessage();
        verify(request).setAttribute("message", SHOULD_BE_NOT_EMPTY_MESSAGE);
    }

    @Test
    public void setExplainingMessageShouldSetRelevantMessageIfUserIsNotValid() throws Exception {
        when(RequestParamValidator.areEmpty(anyVararg())).thenReturn(false);
        when(userService.isValid(anyString(), anyString())).thenReturn(false);
        command.setExplainingMessage();
        verify(request).setAttribute("message", INVALID_USER_MESSAGE);
    }

    @Test
    public void sendToRelevantPageShouldForwardToAuthenticationPage() throws Exception {
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(AUTHENTICATION_PAGE, request, response);
    }

    //    @SuppressWarnings("unchecked")
//    private void buildCommand(String documentId, String password) {
//        when(request.getParameter("documentId")).thenReturn(documentId);
//        when(request.getParameter("password")).thenReturn(password);
//        command = new AuthenticationCommand<>(request, userService);
//    }
}