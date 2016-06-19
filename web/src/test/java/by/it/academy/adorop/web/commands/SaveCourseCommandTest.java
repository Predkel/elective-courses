package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SaveCourseCommandTest extends BasicCommandVerifyingRequestTest {

    private static final String PATH = "path";
    @Mock
    private TeacherService teacherService;

    @Before
    public void setUp() throws Exception {
        command = new SaveCourseCommand(request, teacherService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenTitleParameterIsEmpty() throws Exception {
        PowerMockito.when(RequestParamValidator.isEmpty(anyString())).thenReturn(true);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void requestIsValidShouldReturnTrueWhenTitleParameterIsNotEmpty() throws Exception {
        PowerMockito.when(RequestParamValidator.isEmpty(anyString())).thenReturn(false);
        assertTrue(command.requestIsValid());
    }

    @Test
    public void setExplainingMessageShouldSetShouldBeNotEmptyMessage() throws Exception {
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.SHOULD_BE_NOT_EMPTY_MESSAGE);
    }

    @Test
    public void sendToRelevantPageShouldForwardToAddCourse() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(PATH);
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_ADD_COURSE);
        Dispatcher.forward(PATH, request, response);
    }

    @Test
    public void prepareResponseShouldAddNewCourse() throws Exception {
        command.prepareResponse();
        verify(teacherService).addCourse(anyObject(), anyObject());
    }

    @Test
    public void moveShouldRedirectToMain() throws Exception {
        PowerMockito.when(PathBuilder.buildPathToMain(request)).thenReturn(PATH);
        command.move(response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPathToMain(request);
        verify(response).sendRedirect(PATH);
    }
}