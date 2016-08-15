package by.it.academy.adorop.web.infrastructure.resolvers.implementations;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.infrastructure.resolvers.annotations.ModelById;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class ModelByIdHandlerMethodArgumentResolverTest {
    public static final String NOT_NUMERIC_STRING = "abc";
    private static final String NUMERIC_STRING = "10";
    public static final Course ANY_ENTITY = new Course();
    public static final Class<Service> ANY_CLASS = Service.class;

    private ModelByIdHandlerMethodArgumentResolver resolver;

    @Mock
    private MethodParameter methodParameter;
    @Mock
    private ModelAndViewContainer modelAndViewContainer;
    @Mock
    private NativeWebRequest request;
    @Mock
    private WebDataBinderFactory factory;
    @Mock
    private ModelById modelById;
    @Mock
    Service service;
    @Mock
    ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolver = new ModelByIdHandlerMethodArgumentResolver(applicationContext);
    }

    @Test(expected = TypeMismatchException.class)
    public void resolveArgumentShouldThrowTypeMismatchExceptionWhenMarkIdIsNotPositiveNumber() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NOT_NUMERIC_STRING);
        invokeResolveArgument();
    }

    private void whenEntityWithGivenIdDoesNotExist() {
        when(request.getParameter(anyString())).thenReturn(NUMERIC_STRING);
        when(service.find(anyLong())).thenReturn(null);
    }


    private Object invokeResolveArgument() throws Exception {
        when(methodParameter.getParameterAnnotation(anyObject())).thenReturn(modelById);
        when(modelById.nameOfIdParameter()).thenReturn(anyString());
        return resolver.resolveArgument(methodParameter, modelAndViewContainer, request, factory);
    }

    @Test
    public void supportsShouldReturnFalseWhenMethodParameterIsNotAnnotatedWithModelById() throws Exception {
        when(methodParameter.getParameterAnnotation(anyObject())).thenReturn(null);
        assertFalse(resolver.supportsParameter(methodParameter));
    }
}