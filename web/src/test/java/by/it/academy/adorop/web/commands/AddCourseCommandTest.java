package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.verify;

public class AddCourseCommandTest extends BasicCommandTest {

    private static final String PATH = "/teachers";

    @Before
    public void setUp() throws Exception {
        command = new AddCourseCommand(request);
    }

    @Test
    public void prepareResponseShouldSetPathToProcessForm() throws Exception {
        command.prepareResponse();
        verify(request).setAttribute("processFormPath", PATH);
    }

    @Test
    public void moveShouldForwardToAddCourseForm() throws Exception {
        command.move(response);
        PowerMockito.verifyStatic();
        Dispatcher.forward(Constants.ADD_COURSE_PAGE, request, response);
    }
}