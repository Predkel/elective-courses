package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.web.config.handlers.api.ByIdHandlerMethodArgumentResolverStrategy;
import by.it.academy.adorop.web.config.handlers.api.MarkByIdHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MarkByIdHandlerMethodArgumentResolverImpl implements MarkByIdHandlerMethodArgumentResolver {

    private final ByIdHandlerMethodArgumentResolverStrategy strategy;
    private final MarkService markService;

    @Autowired
    public MarkByIdHandlerMethodArgumentResolverImpl(ByIdHandlerMethodArgumentResolverStrategy strategy, MarkService markService) {
        this.strategy = strategy;
        this.markService = markService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        strategy.setParameterClass(Mark.class);
        return strategy.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        strategy.setService(markService);
        return strategy.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }
}
