package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.web.config.handlers.annotations.ModelById;
import by.it.academy.adorop.web.config.handlers.api.ByIdHandlerMethodArgumentResolverStrategy;
import by.it.academy.adorop.web.config.handlers.api.MarkByIdHandlerMethodArgumentResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class MarkByIdHandlerMethodArgumentResolverImplTest extends AbstractByIdHandlerMethodArgumentResolverImplTest {

    private MarkByIdHandlerMethodArgumentResolver resolver;
    @Mock
    private MarkService markService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        resolver = new MarkByIdHandlerMethodArgumentResolverImpl(strategy, markService);
    }

    @Test
    public void supportsParameterShouldPassClassOfEntityToStrategy() throws Exception {
        resolver.supportsParameter(methodParameter);
        verify(strategy).setParameterClass(Mark.class);
    }

    @Test
    public void resolveArgumentShouldPassMarkServiceToStrategy() throws Exception {
        resolver.resolveArgument(methodParameter, modelAndViewContainer, request, factory);
        verify(strategy).setService(markService);
    }
}