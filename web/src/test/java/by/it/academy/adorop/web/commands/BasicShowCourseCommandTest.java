package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.CourseSecurity;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;
import by.it.academy.adorop.web.utils.PathBuilder;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CommandsFactory.class, CourseSecurity.class,
        Dispatcher.class, PathBuilder.class, IdValidator.class})
public abstract class BasicShowCourseCommandTest {

    BasicShowCourseCommand command;
    @Mock
    MarkService markService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    IdValidator<Course> idValidator;
    @Mock
    Course course;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CommandsFactory.class, CourseSecurity.class,
                Dispatcher.class, PathBuilder.class, IdValidator.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldForwardToMainWithMessageWhenCourseIdIsNotValid() throws ServiceException, ServletException, IOException {
        when(idValidator.isValid(anyString())).thenReturn(false);
        command.execute(request, response);
        verify(idValidator).isValid(anyString());
        PowerMockito.verifyStatic();
        Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
    }
}