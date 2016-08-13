package by.it.academy.adorop.service.exceptions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLayerExceptionHandler {

    @Around("this(by.it.academy.adorop.service.api.Service)")
    public Object doCatch(ProceedingJoinPoint pjp) {
        try {
            Object[] args = pjp.getArgs();
            return pjp.proceed(args);
        } catch (Throwable throwable) {
            throw new ServiceException(throwable);
        }
    }
}
