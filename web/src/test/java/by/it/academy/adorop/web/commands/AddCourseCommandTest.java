package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Dispatcher;
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
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Dispatcher.class, PathBuilder.class})
public class AddCourseCommandTest {

    private static final String SERVLET_PATH = "servletPath";

    private Command command;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Dispatcher.class, PathBuilder.class);
        command = new AddCourseCommand();
    }

    @Test
    public void shouldForwardToAddCourseForm() throws ServletException, IOException {
        command.execute(request, response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Command.ADD_COURSE_FORM, request, response);
    }

    @Test
    public void shouldPutProcessFormPath() {
        when(request.getServletPath()).thenReturn(SERVLET_PATH);
        command.execute(request, response);
        verify(request).setAttribute("processFormPath", SERVLET_PATH);
    }
}