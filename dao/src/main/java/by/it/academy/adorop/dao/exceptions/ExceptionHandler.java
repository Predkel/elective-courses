package by.it.academy.adorop.dao.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ExceptionHandler {

    @Around("this(by.it.academy.adorop.dao.api.DAO)")
    public Object doCatch(ProceedingJoinPoint pjp) {
        try {
            Object[] args = pjp.getArgs();
            return pjp.proceed(args);
        } catch (Throwable throwable) {
            throw new DaoException(throwable);
        }
    }
}
