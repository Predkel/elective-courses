package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.TeacherServiceImpl;
import by.it.academy.adorop.web.utils.*;
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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Dispatcher.class, PathBuilder.class,
        RequestParamValidator.class, CourseSecurity.class})
public class EvaluateCommandTest {

    private static final String COURSE_ID_PARAM = "courseIdParam";
    private static final String PATH_TO_SHOW_COURSE = "pathToShowCourse";
    private Command command;
    @Mock
    private IdValidator<Course> courseIdValidator;
    @Mock
    private IdValidator<Mark> markIdValidator;
    @Mock
    private TeacherServiceImpl teacherService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Dispatcher.class, PathBuilder.class,
                CourseSecurity.class, RequestParamValidator.class);
        command = new EvaluateCommand(courseIdValidator, markIdValidator, teacherService);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("courseId")).thenReturn(COURSE_ID_PARAM);
    }

    @Test
    public void shouldForwardToMainWithFollowTheLinkMessageWhenCourseIdIsNotValid() throws ServiceException, ServletException, IOException {
        when(courseIdValidator.isValid(anyString())).thenReturn(false);
        command.execute(request, response);
        PowerMockito.verifyStatic();
        Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
        verify(request).getParameter("courseId");
    }

    @Test
    public void shouldForwardToTheSamePageWithShouldBeANumberMessageWhenMarkValueIsNotNumberBetweenZeroAndTen() throws ServletException, IOException, ServiceException {
        whenIdsAreValid();
        PowerMockito.when(RequestParamValidator.isNumberBetweenZeroAndTen(anyString())).thenReturn(false);
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(),anyString(), anyString()))
                .thenReturn(PATH_TO_SHOW_COURSE);
        command.execute(request, response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Command.OPERATION_SHOW_COURSE, "courseId", COURSE_ID_PARAM);
        Dispatcher.forwardWithMessage(PATH_TO_SHOW_COURSE, request, response,
                Command.SHOULD_BE_A_NUMBER_MESSAGE);
    }

//    @TestData
//    public void shouldEvaluateWhenMarkValueIsValid() throws ServiceException, IOException {
//        whenRequestIsValid();
//        Mark mark = new Mark();
//        when(markIdValidator.getValidModel()).thenReturn(mark);
//        command.execute(request, response);
//        verify(teacherService).evaluate(mark);
//    }
//
//    @TestData
//    public void shouldRedirectToTheSamePageWhenMarkValueIsValid() throws ServiceException, IOException {
//        whenRequestIsValid();
//        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString(), anyString(), anyString()))
//                .thenReturn(PATH_TO_SHOW_COURSE);
//        command.execute(request, response);
//        verify(response).sendRedirect(PATH_TO_SHOW_COURSE);
//        PowerMockito.verifyStatic();
//        PathBuilder.buildPath(request, Command.OPERATION_SHOW_COURSE, "courseId", COURSE_ID_PARAM);
//    }

    private void whenIdsAreValid() throws ServiceException {
        when(courseIdValidator.isValid(anyString())).thenReturn(true);
        when(markIdValidator.isValid(anyString())).thenReturn(true);
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(true);
    }
}