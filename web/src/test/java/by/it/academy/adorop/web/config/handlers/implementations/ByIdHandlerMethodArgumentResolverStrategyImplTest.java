package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.config.handlers.implementations.ByIdHandlerMethodArgumentResolverStrategyImpl;
import by.it.academy.adorop.web.config.handlers.annotations.ModelById;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ResponseEntity.class})
public class ByIdHandlerMethodArgumentResolverStrategyImplTest {
    public static final String NOT_NUMERIC_STRING = "abc";
    private static final String NUMERIC_STRING = "10";
    private ByIdHandlerMethodArgumentResolverStrategyImpl resolver;
    @Mock
    private Service service;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(ResponseEntity.class);
        resolver = new ByIdHandlerMethodArgumentResolverStrategyImpl();
        resolver.setService(service);
    }

    @Test(expected = TypeMismatchException.class)
    public void resolveArgumentShouldThrowTypeMismatchExceptionWhenMarkIdIsNotPositiveNumber() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NOT_NUMERIC_STRING);
        invokeResolveArgument();
    }
    @Test(expected = TypeMismatchException.class)
    @SuppressWarnings("unchecked")
    public void resolveArgumentShouldThrowTypeMismatchExceptionWhenMarkWithGivenIdDoesNotExist() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NUMERIC_STRING);
        when(service.find(anyLong())).thenReturn(null);
        invokeResolveArgument();
        PowerMockito.verifyStatic();
        ResponseEntity.badRequest();
    }

    @Test
    public void testResolveArgumentShouldReturnMarkIfRequestIsValid() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NUMERIC_STRING);
        Mark expectedMark = new Mark();
        when(service.find(anyLong())).thenReturn(expectedMark);
        assertEquals(expectedMark, invokeResolveArgument());
    }

    private Object invokeResolveArgument() throws Exception {
        when(methodParameter.getParameterAnnotation(anyObject())).thenReturn(modelById);
        when(modelById.nameOfIdParameter()).thenReturn(NOT_NUMERIC_STRING);
        return resolver.resolveArgument(methodParameter, modelAndViewContainer, request, factory);
    }
}