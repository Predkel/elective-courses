package by.it.academy.adorop.web.infrostructure;

import org.hibernate.HibernateException;
import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateExceptionHandlerAdviceTest {

    @Test
    public void shouldReturnErrorPage() throws Exception {
        HibernateExceptionHandlerAdvice handlerAdvice = new HibernateExceptionHandlerAdvice();
        assertEquals("error", handlerAdvice.handleHibernateException(new HibernateException("")));
    }
}