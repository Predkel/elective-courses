package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.CourseSecurity;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowCourseForTeacherCommandTest extends BasicShowCourseCommandTest {

    @Mock
    Teacher teacher;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        command = new ShowCourseForTeacherCommand(markService, idValidator);
        when(session.getAttribute("teacher")).thenReturn(teacher);
        when(course.getTeacher()).thenReturn(teacher);
    }

    @Test
    public void shouldPutIntoRequestMarksOfTheCourseAndPathToProcessEvaluationWhenTeacherIsTeacherOfRequestedCourse() throws ServiceException {
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(true);
        command.setContent(request, course);
        PowerMockito.verifyStatic();
        CourseSecurity.isTeacherOfTheCourse(teacher, course);
        verify(request).setAttribute("marks", markService.getByCourse(course));
        verify(request).setAttribute("action", request.getServletPath());
    }

    @Test
    public void shouldForwardToMainWithFollowTheLinkMessageWhenTeacherIsNotTeacherOfRequestedCourse() throws ServiceException, ServletException, IOException {
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(false);
        command.forward(request, response, course);
        PowerMockito.verifyStatic();
        CourseSecurity.isTeacherOfTheCourse(teacher, course);
        Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
    }

    @Test
    public void shouldForwardToCourseForTeacherPageWhenTeacherIsTheTeacherOfTheCurrentCourse() throws ServletException, IOException {
        PowerMockito.when(CourseSecurity.isTeacherOfTheCourse(anyObject(), anyObject())).thenReturn(true);
        command.forward(request, response, course);
        PowerMockito.verifyStatic();
        CourseSecurity.isTeacherOfTheCourse(teacher, course);
        Dispatcher.forward(Command.COURSE_FOR_TEACHER_PAGE, request, response);
    }
}