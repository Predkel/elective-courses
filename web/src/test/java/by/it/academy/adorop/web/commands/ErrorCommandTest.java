package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

public class ErrorCommandTest extends BasicTest {

    private Command command;

    @Before
    public void setUp() throws Exception {
        super.setup();
        command = new ErrorCommand(request);
    }

    @Test
    public void shouldForwardToErrorPage() throws Exception {
        command.execute(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.ERROR_PAGE, request, response);
    }
}