package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.service.utils.ServiceUtils;
import by.it.academy.adorop.web.commands.Command;
import by.it.academy.adorop.web.commands.CommandsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CommandsFactory.class, ServiceUtils.class})
public class ControllersTest {

    private Controller controller;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Command command;
    @Mock
    private Command errorCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(CommandsFactory.class, ServiceUtils.class);
        controller = new Controller();
        when(CommandsFactory.createErrorCommand()).thenReturn(errorCommand);
    }

    @Test
    public void doGetShouldCreateAndExecuteErrorCommandWhenExceptionWasThrown() throws Exception {
        when(CommandsFactory.createCommand(request)).thenThrow(new RuntimeException());
        controller.doGet(request, response);
        PowerMockito.verifyStatic();
        CommandsFactory.createErrorCommand();
        Mockito.verify(errorCommand).execute(request, response);
    }

    @Test
    public void doPostShouldCreateAndExecuteErrorCommandWhenExceptionWasThrown() throws Exception {
        when(CommandsFactory.createCommand(request)).thenThrow(new RuntimeException());
        controller.doPost(request, response);
        PowerMockito.verifyStatic();
        CommandsFactory.createErrorCommand();
        Mockito.verify(errorCommand).execute(request, response);
    }

    @Test
    public void destroyShouldReleaseResources() throws Exception {
        controller.destroy();
        PowerMockito.verifyStatic();
        ServiceUtils.releaseResources();
    }
}