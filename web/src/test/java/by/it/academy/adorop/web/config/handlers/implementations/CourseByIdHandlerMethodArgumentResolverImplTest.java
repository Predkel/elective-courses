package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.config.handlers.api.CourseByIdHandlerMethodArgumentResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseByIdHandlerMethodArgumentResolverImplTest extends AbstractByIdHandlerMethodArgumentResolverImplTest {
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