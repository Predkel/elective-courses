package by.it.academy.adorop.web.infrastructure.http.method.handlers.get;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.web.infrastructure.filtering.RestrictionsParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractGetHandler<T, ID extends Serializable> implements GetHandler<ID> {
    private final RestrictionsParser restrictionsParser;

    protected AbstractGetHandler(RestrictionsParser restrictionsParser) {
        this.restrictionsParser = restrictionsParser;
    }

    @Override
    public ResponseEntity getBy(Map<String, String> properties) {
        List<T> responseContent = getService().findBy(restrictionsParser.parse(properties, getEntityClass()));
        if (responseContent.isEmpty()) {
            return returnNotFound("{message : Resource with given parameters not found}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseContent);
    }

    protected abstract Class<T> getEntityClass();

    @Override
    public ResponseEntity get(ID id) {
        T responseContent = getService().find(id);
        return responseContent == null ? returnNotFound("{message : Resource with given id(" + id + ")not found}")
                : ResponseEntity.status(HttpStatus.OK).body(responseContent);
    }

    private ResponseEntity<String> returnNotFound(String body) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    protected abstract Service<T, ID> getService();
}
