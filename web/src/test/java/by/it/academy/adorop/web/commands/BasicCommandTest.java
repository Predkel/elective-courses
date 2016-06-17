package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.commands.Command;
import by.it.academy.adorop.web.utils.CourseSecurity;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestParamValidator.class, PathBuilder.class, Dispatcher.class, CourseSecurity.class})
public class BasicCommandTest {

    Command command;

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RequestParamValidator.class, PathBuilder.class, Dispatcher.class, CourseSecurity.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testGetLogger() throws Exception {
        assertNotNull(command.getLogger());
    }
}
