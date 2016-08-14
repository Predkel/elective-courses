package by.it.academy.adorop.web.config;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HandlerUnExpectedExceptionResolver extends DefaultHandlerExceptionResolver {
    private static final Logger LOGGER = Logger.getLogger("by.it.academy.adorop.web.config.HandlerUnExpectedExceptionResolver");

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView expectedExceptionHandling = super.doResolveException(request, response, handler, ex);
        return expectedExceptionHandling == null ? expectedExceptionHandling : handleException(ex);
    }

    ModelAndView handleException(Exception e) {
        LOGGER.error(e);
        return new ModelAndView("error");
    }
}
