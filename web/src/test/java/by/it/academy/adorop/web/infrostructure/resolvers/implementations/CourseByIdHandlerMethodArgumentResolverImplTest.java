package by.it.academy.adorop.web.infrostructure.resolvers.implementations;

import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.Service;
import org.mockito.Mock;

public class CourseByIdHandlerMethodArgumentResolverImplTest extends AbstractModelByIdHandlerMethodArgumentResolverTest {
    @Mock
    private CourseService courseService;

    @Override
    protected Service getService() {
        return courseService;
    }

    @Override
    protected AbstractModelByIdHandlerMethodArgumentResolver getResolver() {
        return new CourseByIdHandlerMethodArgumentResolverImpl(courseService);
    }
}