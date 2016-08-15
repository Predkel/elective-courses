package by.it.academy.adorop.web.infrastructure;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExceptionHandlerAdviceTest {

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
    public void catchTypeMismatchExceptionShouldReturnBadRequestPage() throws Exception {
        when(request.getServletPath()).thenReturn("teachers");
        assertEquals("badRequest", handlerAdvice.catchTypeMismatchException(new Exception(), model, request));
        verify(model).addAttribute("pathToMain", "/teachers");
    }

    @Test
    public void catchAccessDeniedExceptionShouldReturnBadRequestPage() throws Exception {
        when(request.getServletPath()).thenReturn("students");
        assertEquals("badRequest", handlerAdvice.catchAccessDeniedException(new Exception(), model, request));
        verify(model).addAttribute("pathToMain", "/students");
    }
}