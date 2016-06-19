package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

public abstract class BasicShowCourseCommandTest extends BasicCommandVerifyingRequestTest {

    private static final String PATH_TO_MAIN = "path to main";

    @Test
    public void setExplainingMessageShouldSetFollowTheLinkMessage() throws Exception {
        command.setExplainingMessage();
        verify(request).setAttribute("message", Constants.FOLLOW_THE_LINK_MESSAGE);
    }

    @Test
    public void sendToRelevantPageShouldForwardToMain() throws Exception {
        PowerMockito.when(PathBuilder.buildPath(anyObject(), anyString())).thenReturn(PATH_TO_MAIN);
        command.sendToRelevantPage(response);
        PowerMockito.verifyStatic();
        PathBuilder.buildPath(request, Constants.OPERATION_MAIN);
        Dispatcher.forward(PATH_TO_MAIN, request, response);
    }
}