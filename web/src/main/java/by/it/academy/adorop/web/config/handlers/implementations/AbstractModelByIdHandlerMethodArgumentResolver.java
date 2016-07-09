package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.config.handlers.annotations.ModelById;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public abstract class AbstractModelByIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ModelById.class) != null
                && parameter.getParameterType().equals(getClassOfParameter());
    }

    protected abstract Class<?> getClassOfParameter();

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String nameOfIdParameter = getNameOfIdParameter(parameter);
        String idParameter = webRequest.getParameter(nameOfIdParameter);
        if (!RequestParamValidator.isPositiveNumber(idParameter)) {
            throw new TypeMismatchException(idParameter, Long.class);
        }
        Object entityParameter = getService().find(Long.valueOf(idParameter));
        if (entityParameter == null) {
            //TODO another exception
            throw new TypeMismatchException(idParameter, Long.class);
        }
        return entityParameter;
    }

    protected abstract Service getService();

    private String getNameOfIdParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ModelById.class).nameOfIdParameter();
    }
}
