package by.it.academy.adorop.web.config.handlers.api;

import by.it.academy.adorop.service.api.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

public interface ByIdHandlerMethodArgumentResolverStrategy extends HandlerMethodArgumentResolver {
    void setService(Service service);
    void setParameterClass(Class parameterClass);
}
