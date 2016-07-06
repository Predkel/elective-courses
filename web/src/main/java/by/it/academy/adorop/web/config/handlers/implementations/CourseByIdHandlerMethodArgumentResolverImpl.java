package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.web.config.handlers.api.ByIdHandlerMethodArgumentResolverStrategy;
import by.it.academy.adorop.web.config.handlers.api.CourseByIdHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CourseByIdHandlerMethodArgumentResolverImpl implements CourseByIdHandlerMethodArgumentResolver {

    private final ByIdHandlerMethodArgumentResolverStrategy strategy;
    private final CourseService courseService;

    @Autowired
    public CourseByIdHandlerMethodArgumentResolverImpl(ByIdHandlerMethodArgumentResolverStrategy strategy, CourseService courseService) {
        this.strategy = strategy;
        this.courseService = courseService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        strategy.setParameterClass(Course.class);
        return strategy.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        strategy.setService(courseService);
        return strategy.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }
}
