package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.web.config.handlers.api.CourseByIdHandlerMethodArgumentResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class CourseByIdHandlerMethodArgumentResolverImplTest extends AbstractByIdHandlerMethodArgumentResolverImplTest {
    CourseByIdHandlerMethodArgumentResolver resolver;
    @Mock
    private CourseService courseService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolver = new CourseByIdHandlerMethodArgumentResolverImpl(strategy, courseService);
    }

    @Test
    public void supportsParameterShouldPassClassOfEntityToStrategy() throws Exception {
        resolver.supportsParameter(methodParameter);
        verify(strategy).setParameterClass(Course.class);
    }
    @Test
    public void resolveArgumentShouldPassMarkServiceToStrategy() throws Exception {
        resolver.resolveArgument(methodParameter, modelAndViewContainer, request, factory);
        verify(strategy).setService(courseService);
    }
}