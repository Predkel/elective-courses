package by.it.academy.adorop.dao.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.when;

public class ExceptionHandlerTest {
    private DaoLayerExceptionHandler exceptionHandler;
    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        exceptionHandler = new DaoLayerExceptionHandler();
    }

    @Test(expected = DaoException.class)
    public void shouldRethrowDaoException() throws Throwable {
        when(proceedingJoinPoint.proceed(anyVararg())).thenThrow(new HibernateException(""));
        exceptionHandler.doCatch(proceedingJoinPoint);
    }
}