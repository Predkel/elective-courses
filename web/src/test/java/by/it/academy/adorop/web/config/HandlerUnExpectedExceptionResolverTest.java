package by.it.academy.adorop.web.config;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Logger.class)
public class HandlerUnExpectedExceptionResolverTest {

    private HandlerUnExpectedExceptionResolver exceptionHandlerAdvisor;
    @Mock
    private Logger logger;
    @Mock
    private Exception exception;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Logger.class);
        PowerMockito.when(Logger.getLogger(anyString())).thenReturn(logger);
        exceptionHandlerAdvisor = new HandlerUnExpectedExceptionResolver();
    }

    @Test
    public void shouldLogException() throws Exception {
        exceptionHandlerAdvisor.handleException(exception);
        verify(logger).error(exception);
    }

    @Test
    public void shouldSendUserToErrorPage() throws Exception {
        assertEquals(new ModelAndView("error"), exceptionHandlerAdvisor.handleException(exception));
    }
}