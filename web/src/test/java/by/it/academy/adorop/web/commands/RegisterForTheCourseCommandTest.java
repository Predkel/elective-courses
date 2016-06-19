package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterForTheCourseCommandTest extends BasicCommandVerifyingRequestTest {

    private static final String COURSE_ID_PARAMETER = "1";
    public static final String ANY_PATH = "any path";
    @Mock
    private CourseService courseService;
    @Mock
    private StudentService studentService;
    @Mock
    private MarkService markService;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("courseId")).thenReturn(COURSE_ID_PARAMETER);
        command = new RegisterForTheCourseCommand(request, courseService, studentService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenCourseIdIsNotValidId() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(false);
        assertFalse(command.requestIsValid());
    }
    
    @Test
    public void requestIsValidShouldReturnFalseWhenStudentIsAlreadyCourseListener() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(true);
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(true);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void testRequestIsValidOnPositiveScenario() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(true);
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(false);
        assertTrue(command.requestIsValid());
    }

    @Test
    public void setExplainingMessageShouldSetRegisterForTheCourseMessage() throws Exception {
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.FAILED_REGISTER_FOR_THE_COURSE_MESSAGE);
    }

    @Test
    public void sendToRelevantPageShouldForwardToMain() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(ANY_PATH);
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_MAIN);
        Dispatcher.forward(ANY_PATH, request, response);
    }

    @Test
    public void prepareResponseShouldRegisterStudentForTheCourse() throws Exception {
        Student student = new Student();
        when(session.getAttribute("student")).thenReturn(student);
        Course course = new Course();
        when(courseService.find(anyLong())).thenReturn(course);
        command.prepareResponse();
        verify(studentService).registerForTheCourse(student, course);
    }

    @Test
    public void moveShouldRedirectToShowTheSameCourse() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(any(), any(), any(), any())).thenReturn(ANY_PATH);
        command.move(response);
        verify(response).sendRedirect(ANY_PATH);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_SHOW_COURSE, "courseId", COURSE_ID_PARAMETER);
    }
}