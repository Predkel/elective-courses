package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.config.handlers.api.CourseByIdHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseByIdHandlerMethodArgumentResolverImpl extends AbstractByIdHandlerMethodArgumentResolver implements CourseByIdHandlerMethodArgumentResolver {

    private final CourseService courseService;

    @Autowired
    public CourseByIdHandlerMethodArgumentResolverImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected Class<?> getClassOfParameter() {
        return Course.class;
    }

    @Override
    protected Service getService() {
        return courseService;
    }
}
