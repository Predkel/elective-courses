package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.implementations.MarkServiceImpl;
import by.it.academy.adorop.web.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EvaluateCommandTest extends BasicCommandVerifyingRequestTest {

    public static final String COURSE_ID_PARAMETER = "1";
    public static final String MARK_ID_PARAMETER = "2";
    public static final String MARK_VALUE_PARAMETER = "3";
    private static final String ANY_PATH = "any path";
    @Mock
    private TeacherService teacherService;
    @Mock
    private CourseService courseService;
    @Mock
    private MarkService markService;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("courseId")).thenReturn(COURSE_ID_PARAMETER);
        when(request.getParameter("markId")).thenReturn(MARK_ID_PARAMETER);
        when(request.getParameter("markValue")).thenReturn(MARK_VALUE_PARAMETER);
        command = new EvaluateCommand(request, teacherService, courseService, markService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenCourseIdOrMarkIdParameterIsNotValidId() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(COURSE_ID_PARAMETER, courseService)).thenReturn(false);
        assertFalse(command.requestIsValid());
        PowerMockito.verifyStatic();
        RequestParamValidator.isValidId(COURSE_ID_PARAMETER, courseService);
        RequestParamValidator.isValidId(MARK_ID_PARAMETER, markService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenMarkValueParameterIsNotNumberFromZeroToTen() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(true);
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(true);
        PowerMockito.when(RequestParamValidator.isNumberBetweenZeroAndTen(anyString())).thenReturn(false);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenTeacherIsNotTeacherOfRequestedCourse() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(any(), anyObject())).thenReturn(true);
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(false);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void testRequestIsValidOnPositiveScenario() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(true);
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(true);
        assertTrue(command.requestIsValid());
    }

    @Test
    public void setExplainingMessageShouldSetEvaluateMessageWhenTeacherAttemptedEvaluateStudentThroughURL() throws Exception {
        PowerMockito.when(RequestParamValidator.isNumberBetweenZeroAndTen(anyString())).thenReturn(true);
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.FAILED_EVALUATE_MESSAGE);
    }

    @Test
    public void setExplainingMessageShouldSetMarkValueMessageWhenMarkValueIsNotValid() throws Exception {
        PowerMockito.when(RequestParamValidator.isNumberBetweenZeroAndTen(anyString())).thenReturn(false);
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.MARK_VALUE_MESSAGE);
    }

    @Test
    public void sendToRelevantPageShouldForwardToMainWhenTeacherAttemptedEvaluateStudentThroughURL() throws Exception {
        PowerMockito.when(RequestParamValidator.isNumberBetweenZeroAndTen(anyString())).thenReturn(true);
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        Dispatcher.forwardToMain(request, response);
    }

    @Test
    public void sendToRelevantPageShouldForwardToTheSamePageWhenMarkValueIsNotValid() throws Exception {
        PowerMockito.when(RequestParamValidator.isNumberBetweenZeroAndTen(anyString())).thenReturn(false);
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(), anyString(), anyString())).thenReturn(ANY_PATH);
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(ANY_PATH, request, response);
    }

    @Test
    public void prepareResponseShouldEvaluateStudent() throws Exception {
        Mark mark = new Mark();
        when(markService.find(anyLong())).thenReturn(mark);
        command.prepareResponse();
        verify(teacherService).evaluate(mark);
    }

    @Test
    public void moveShouldRedirectToTheSamePage() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(), anyString(), anyString())).thenReturn(ANY_PATH);
        command.move(response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_SHOW_COURSE, "courseId", COURSE_ID_PARAMETER);
        verify(response).sendRedirect(ANY_PATH);
    }
}