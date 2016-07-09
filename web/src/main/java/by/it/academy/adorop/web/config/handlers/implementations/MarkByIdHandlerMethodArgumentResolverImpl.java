package by.it.academy.adorop.web.config.handlers.implementations;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.config.handlers.api.MarkByIdHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarkByIdHandlerMethodArgumentResolverImpl extends AbstractModelByIdHandlerMethodArgumentResolver implements MarkByIdHandlerMethodArgumentResolver {

    private final MarkService markService;

    @Autowired
    public MarkByIdHandlerMethodArgumentResolverImpl(MarkService markService) {
        this.markService = markService;
    }

    @Override
    protected Class<?> getClassOfParameter() {
        return Mark.class;
    }

    @Override
    protected Service getService() {
        return markService;
    }
}
