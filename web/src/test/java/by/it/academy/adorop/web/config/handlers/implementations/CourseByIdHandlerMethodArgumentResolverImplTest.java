package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.Service;
import org.mockito.Mock;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import static org.mockito.Mockito.verify;

public class CourseByIdHandlerMethodArgumentResolverImplTest extends AbstractModelByIdHandlerMethodArgumentResolverTest {
    @Mock
    private CourseService courseService;

    @Override
    protected Service getService() {
        return courseService;
    }

    @Override
    protected HandlerMethodArgumentResolver getResolver() {
        return new CourseByIdHandlerMethodArgumentResolverImpl(courseService);
    }
}