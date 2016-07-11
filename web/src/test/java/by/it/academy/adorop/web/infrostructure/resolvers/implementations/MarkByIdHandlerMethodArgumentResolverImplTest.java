package by.it.academy.adorop.web.infrostructure.resolvers.implementations;

import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.Service;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class MarkByIdHandlerMethodArgumentResolverImplTest extends AbstractModelByIdHandlerMethodArgumentResolverTest {

    @Mock
    private MarkService markService;

    @Override
    protected Service getService() {
        return markService;
    }

    @Override
    protected AbstractModelByIdHandlerMethodArgumentResolver getResolver() {
        return new MarkByIdHandlerMethodArgumentResolverImpl(markService);
    }
}