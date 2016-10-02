package by.it.academy.adorop.web.infrastructure.http.method.handlers.get;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.infrastructure.filtering.RestrictionsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("markGetHandler")
public class MarkGetHandler extends AbstractGetHandler<Mark, Long> implements GetHandler<Long> {
    private final MarkService markService;

    @Autowired
    public MarkGetHandler(MarkService markService, RestrictionsParser restrictionsParser) {
        super(restrictionsParser);
        this.markService = markService;
    }

    @Override
    protected Class<Mark> getEntityClass() {
        return Mark.class;
    }

    @Override
    protected Service<Mark, Long> getService() {
        return markService;
    }
}
