package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.web.config.handlers.api.ByIdHandlerMethodArgumentResolverStrategy;
import org.mockito.Mock;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public abstract class AbstractByIdHandlerMethodArgumentResolverImplTest {
    @Mock
    protected ByIdHandlerMethodArgumentResolverStrategy strategy;
    @Mock
    protected MethodParameter methodParameter;
    @Mock
    protected ModelAndViewContainer modelAndViewContainer;
    @Mock
    protected NativeWebRequest request;
    @Mock
    protected WebDataBinderFactory factory;
}
