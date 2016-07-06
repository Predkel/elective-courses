package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.config.handlers.annotations.ModelById;
import by.it.academy.adorop.web.config.handlers.api.ByIdHandlerMethodArgumentResolverStrategy;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Component
public class ByIdHandlerMethodArgumentResolverStrategyImpl implements ByIdHandlerMethodArgumentResolverStrategy {

    private Service service;
    private Class parameterClass;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ModelById.class) != null && parameter.getParameterType().equals(parameterClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String nameOfIdParameter = getNameOfIdParameter(parameter);
        String idParameter = webRequest.getParameter(nameOfIdParameter);
        if (!RequestParamValidator.isPositiveNumber(idParameter)) {
            throw new TypeMismatchException(idParameter, Long.class);
        }
        Object entityParameter = service.find(Long.valueOf(idParameter));
        if (entityParameter == null) {
            //TODO another exception
            throw new TypeMismatchException(idParameter, Long.class);
        }
        return entityParameter;
    }

    private String getNameOfIdParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ModelById.class).nameOfIdParameter();
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void setParameterClass(Class parameterClass) {
        this.parameterClass = parameterClass;
    }
}
