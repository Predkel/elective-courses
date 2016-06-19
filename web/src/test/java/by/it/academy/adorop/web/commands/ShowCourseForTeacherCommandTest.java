package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.CourseSecurity;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowCourseForTeacherCommandTest extends BasicShowCourseCommandTest {
    private static final String NUMERIC_STRING = "1";
    private static final String PATH_TO_CONTROLLER = "/teachers";
    @Mock
    private MarkService markService;
    @Mock
    private CourseService courseService;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("courseId")).thenReturn(NUMERIC_STRING);
        command = new ShowCourseForTeacherCommand(request, markService, courseService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenCourseIdParameterIsNotValidId() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(false);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenTeacherIsNotTeacherOfTheCourse() throws Exception {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(true);
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(false);
        assertFalse(command.requestIsValid());
    }

    @Test
    public void testRequestIsValidOnPositiveScenario() throws ServiceException {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(true);
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(true);
        assertTrue(command.requestIsValid());
    }

    @Test
    public void moveShouldForwardToCourseForTeacherPage() throws Exception {
        command.move(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.COURSE_FOR_TEACHER_PAGE, request, response);
    }

    @Test
    public void setContentShouldSetPathToTeachersController() throws Exception {
        command.setContent();
        verify(request).setAttribute("pathToTeachersController", PATH_TO_CONTROLLER);
    }

    @Test
    public void setContentShouldSetMarksOfGivenCourse() throws Exception {
        ArrayList<Mark> marks = new ArrayList<>();
        when(markService.getByCourse(anyObject())).thenReturn(marks);
        command.setContent();
        verify(request).setAttribute("marks", marks);
    }
}