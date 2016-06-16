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
public class RegisterCommandTest {

    private static final String PROCESS_FORM_PATH = "process form path";
    private Command command;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Dispatcher.class, PathBuilder.class);
        command = new RegisterCommand();
    }

    @Test
    public void shouldForwardToRegisterPage() throws ServletException, IOException {
        command.execute(request, response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Command.REGISTER_FORM, request, response);
    }

    @Test
    public void shouldSetPathToProcessRegistrationForm() {
        when(request.getServletPath()).thenReturn(PROCESS_FORM_PATH);
        command.execute(request, response);
        verify(request).setAttribute("processFormPath", PROCESS_FORM_PATH);
    }
}