package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterCommandTest extends BasicCommandTest {

    private static final String ANY_PATH = "any path";

    @Before
    public void setUp() throws Exception {
        command = new RegisterCommand(request);
    }

    @Test
    public void prepareResponseShouldSetPathToProcessRegistrationForm() throws Exception {
        when(request.getServletPath()).thenReturn(ANY_PATH);
        command.prepareResponse();
        verify(request).setAttribute("processFormPath", ANY_PATH);
    }

    @Test
    public void moveShouldForwardToRegistrationPage() throws Exception {
        command.move(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.REGISTRATION_PAGE, request, response);
    }
}