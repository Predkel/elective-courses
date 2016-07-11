package by.it.academy.adorop.web.infrostructure;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HibernateExceptionHandlerAdvice {

    private final Logger logger = Logger.getLogger(HibernateExceptionHandlerAdvice.class);

    @ExceptionHandler(HibernateException.class)
    public String handleHibernateException(Exception e) {
        logger.error(e);
        return "error";
    }
}
