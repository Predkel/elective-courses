package by.it.academy.adorop.web.infrostructure.resolvers.implementations;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.infrostructure.resolvers.annotations.ModelById;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component("modelByIdHandlerArgumentResolver")
public class ModelByIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private ApplicationContext applicationContext;

    @Autowired
    public ModelByIdHandlerMethodArgumentResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ModelById.class) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ModelById modelByIdAnnotation = parameter.getParameterAnnotation(ModelById.class);
        String idParameter = getIdParameter(webRequest, modelByIdAnnotation);
        if (!RequestParamValidator.isPositiveNumber(idParameter)) {
            throw new TypeMismatchException(idParameter, Long.class);
        }
        Object requestedEntity = getService(modelByIdAnnotation).find(Long.valueOf(idParameter));
        if (requestedEntity == null) {
            //TODO another exception
            throw new TypeMismatchException(idParameter, Long.class);
        }
        return requestedEntity;
    }

    private String getIdParameter(NativeWebRequest webRequest, ModelById modelByIdAnnotation) {
        String nameOfIdParameter = modelByIdAnnotation.nameOfIdParameter();
        return webRequest.getParameter(nameOfIdParameter);
    }

    private Service getService(ModelById modelByIdAnnotation){
        Class<? extends Service> serviceClass = modelByIdAnnotation.serviceClass();
        Service service = applicationContext.getBean(serviceClass);
        if (service == null) {
            throw new NoSuchBeanDefinitionException(serviceClass);
        }
        return service;
    }
}
