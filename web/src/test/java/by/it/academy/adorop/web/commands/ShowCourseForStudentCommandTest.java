package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import javax.servlet.ServletException;
import java.io.IOException;

import static by.it.academy.adorop.web.commands.Command.COURSE_FOR_STUDENTS_PAGE;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowCourseForStudentCommandTest extends BasicShowCourseCommandTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        command = new ShowCourseForStudentCommand(markService, idValidator);
    }

    @Test
    public void shouldForwardToShowCourseForStudentPageWhenCourseIdIsValid() throws ServiceException, ServletException, IOException {
        command.forward(request, response, course);
        PowerMockito.verifyStatic();
        Dispatcher.forward(COURSE_FOR_STUDENTS_PAGE, request, response);
    }

    @Test
    public void shouldPutMarkIntoRequestWhenStudentIsCourseListener() throws ServiceException {
        Mark mark = new Mark();
        when(markService.getByStudentAndCourse(anyObject(), anyObject())).thenReturn(mark);
        command.setContent(request, new Course());
        verify(request).setAttribute("mark", mark);
    }
}