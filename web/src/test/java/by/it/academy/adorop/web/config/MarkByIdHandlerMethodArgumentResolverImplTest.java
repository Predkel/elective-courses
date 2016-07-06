package by.it.academy.adorop.web.config;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ResponseEntity.class})
public class MarkByIdHandlerMethodArgumentResolverImplTest {
    public static final String NOT_NUMERIC_STRING = "abc";
    private static final String NUMERIC_STRING = "10";
    private MarkByIdHandlerMethodArgumentResolver resolver;
    @Mock
    private MarkService markService;
    @Mock
    private MethodParameter methodParameter;
    @Mock
    private ModelAndViewContainer modelAndViewContainer;
    @Mock
    private NativeWebRequest request;
    @Mock
    private WebDataBinderFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(ResponseEntity.class);
        resolver = new MarkByIdHandlerMethodArgumentResolverImpl(markService);
    }

    @Test
    public void resolveArgumentShouldReturnBadRequestResponseCodeWhenMarkIdIsNotPositiveNumber() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NOT_NUMERIC_STRING);
        invokeResolveArgument();
        PowerMockito.verifyStatic();
        ResponseEntity.badRequest();
    }
    @Test
    public void resolveArgumentShouldReturnBadRequestResponseCodeWhenMarkWithGivenIdDoesNotExist() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NUMERIC_STRING);
        when(markService.find(anyLong())).thenReturn(null);
        invokeResolveArgument();
        PowerMockito.verifyStatic();
        ResponseEntity.badRequest();
    }

    @Test
    public void testResolveArgumentShouldReturnMarkIfRequestIsValid() throws Exception {
        when(request.getParameter(anyString())).thenReturn(NUMERIC_STRING);
        Mark expectedMark = new Mark();
        when(markService.find(anyLong())).thenReturn(expectedMark);
        assertEquals(expectedMark, invokeResolveArgument());
    }

    private Object invokeResolveArgument() throws Exception {
        return resolver.resolveArgument(methodParameter, modelAndViewContainer, request, factory);
    }
}