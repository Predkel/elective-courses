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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
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
    public void prepareResponseShouldSetCourse() throws Exception {
        when(courseService.find(anyLong())).thenReturn(COURSE);
        command.prepareResponse();
        verify(request).setAttribute("course", COURSE);
    }

    @Test
    public void prepareResponseShouldSetPathToProcessRegistrationForTheCourse() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(), anyString(), anyString())).thenReturn(PATH);
        command.prepareResponse();
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_REGISTER_FOR_THE_COURSE, "courseId", COURSE_ID);
        verify(request).setAttribute("pathToProcessRegistrationForTheCourse", PATH);
    }

    @Test
    public void prepareResponseShouldSetIsTheStudentListenerOfTheGivenCourse() throws Exception {
        final boolean TRUE = true;
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(TRUE);
        command.prepareResponse();
        verify(request).setAttribute("isCourseListener", TRUE);
    }

    @Test
    public void prepareResponseShouldSetMarkWhenStudentIsCourseListener() throws Exception {
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(true);
        when(markService.getByStudentAndCourse(anyObject(), anyObject())).thenReturn(MARK);
        command.prepareResponse();
        verify(request).setAttribute("mark", MARK);
    }

    @Test
    public void moveShouldForwardToCourseForStudentPage() throws Exception {
        command.move(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.COURSE_FOR_STUDENTS_PAGE, request, response);
    }
}