package by.it.academy.adorop.web.infrastructure.http.method.handlers.get;

import by.it.academy.adorop.service.api.Service;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractGetHandler<T, ID extends Serializable> implements GetHandler<T> {
    @Override
    public ResponseEntity getBy(Map<String, Object> properties) {
        T entity = service().findSingleResultBy(properties);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.OK).body("{}");
        }
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    protected abstract Service<T, ID> service();
}
