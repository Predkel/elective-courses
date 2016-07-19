package by.it.academy.adorop.web.infrostructure;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

public class HibernateExceptionHandlerAdviceTest {

    private ExceptionHandlerAdvice handlerAdvice = new ExceptionHandlerAdvice();
    @Mock
    private Model model;
    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void catchServiceExceptionShouldReturnErrorPage() throws Exception {
        assertEquals("error", handlerAdvice.catchServiceException(new ServiceException(new Throwable())));
    }

    @Test
    public void catchBadRequestExceptionShouldReturnBadRequestPage() throws Exception {
        assertEquals("badRequest", handlerAdvice.catchBadRequestException(new Exception(), model, request));
    }

    @Test
    public void catchAccessDeniedExceptionShouldReturnBadRequestPage() throws Exception {
        assertEquals("badRequest", handlerAdvice.catchAccessDeniedException(new Exception(), model, request));
    }
}