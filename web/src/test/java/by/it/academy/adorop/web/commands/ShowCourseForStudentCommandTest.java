package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowCourseForStudentCommandTest extends BasicShowCourseCommandTest {
    private static final Course COURSE = new Course();
    private static final String COURSE_ID = "1";
    private static final String PATH = "path";
    private static final Mark MARK = new Mark();
    @Mock
    StudentService studentService;
    @Mock
    CourseService courseService;
    @Mock
    MarkService markService;

    @Before
    public void setUp() throws Exception {
        when(request.getParameter("courseId")).thenReturn(COURSE_ID);
        command = new ShowCourseForStudentCommand(request, markService, courseService, studentService);
    }

    @Test
    public void requestIsValidShouldReturnFalseWhenCourseIdParameterIsNotValid() throws Exception {
        whenIsValidCourseIdReturns(false);
        assertFalse(command.requestIsValid());
    }
    @Test
    public void requestIsValidShouldReturnTrueWhenCourseIdParameterIsValid() throws Exception {
        whenIsValidCourseIdReturns(true);
        assertTrue(command.requestIsValid());
    }

    private void whenIsValidCourseIdReturns(boolean value) throws ServiceException {
        PowerMockito.when(RequestParamValidator.isValidId(anyString(), anyObject())).thenReturn(value);
    }

    @Test
    public void setContentShouldSetCourse() throws Exception {
        when(courseService.find(anyLong())).thenReturn(COURSE);
        command.setContent();
        verify(request).setAttribute("course", COURSE);
    }

    @Test
    public void setContentShouldSetPathToProcessRegistrationForTheCourse() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(), anyString(), anyString())).thenReturn(PATH);
        command.setContent();
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_REGISTER_FOR_THE_COURSE, "courseId", COURSE_ID);
        verify(request).setAttribute("pathToProcessRegistrationForTheCourse", PATH);
    }

    @Test
    public void setContentShouldSetIsTheStudentListenerOfTheGivenCourse() throws Exception {
        final boolean TRUE = true;
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(TRUE);
        command.setContent();
        verify(request).setAttribute("isCourseListener", TRUE);
    }

    @Test
    public void setContentShouldSetMarkWhenStudentIsCourseListener() throws Exception {
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(true);
        when(markService.getByStudentAndCourse(anyObject(), anyObject())).thenReturn(MARK);
        command.setContent();
        verify(request).setAttribute("mark", MARK);
    }

    @Test
    public void moveShouldForwardToCourseForStudentPage() throws Exception {
        command.move(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.COURSE_FOR_STUDENTS_PAGE, request, response);
    }
}