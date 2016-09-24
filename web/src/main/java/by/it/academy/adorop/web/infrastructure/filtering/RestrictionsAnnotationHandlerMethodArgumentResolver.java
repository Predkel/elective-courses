package by.it.academy.adorop.web.infrastructure.filtering;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.context.request.NativeWebRequest.*;

@Component("restrictionsAnnotationHandlerMethodArgumentResolver")
@Order(Integer.MAX_VALUE)
public class RestrictionsAnnotationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Restrictions.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Restrictions parameterAnnotation = parameter.getParameterAnnotation(Restrictions.class);
        Class<?> retrievedClass = parameterAnnotation.retrievedClass();
        Field[] declaredFields = retrievedClass.getDeclaredFields();
        String[] attributeNames = webRequest.getAttributeNames(SCOPE_REQUEST);
        for (String attributeName : attributeNames) {
            Object attribute = webRequest.getAttribute(attributeName, SCOPE_REQUEST);
            for (Field declaredField : declaredFields) {
                String fieldName = declaredField.getName();
                if (attributeName.contains(fieldName)) {
                    if (fieldName.equals(attributeName)) {
                        result.put(attributeName, attribute);
                    }
                }
            }
        }
        return result;
    }
}
