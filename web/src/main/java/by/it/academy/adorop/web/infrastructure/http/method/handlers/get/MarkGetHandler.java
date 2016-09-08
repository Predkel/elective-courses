package by.it.academy.adorop.web.infrastructure.http.method.handlers.get;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("markGetHandler")
public class MarkGetHandler extends AbstractGetHandler<Mark, Long> {
    private final MarkService markService;

    @Autowired
    public MarkGetHandler(MarkService markService) {
        this.markService = markService;
    }

    @Override
    protected Service<Mark, Long> service() {
        return markService;
    }
}
