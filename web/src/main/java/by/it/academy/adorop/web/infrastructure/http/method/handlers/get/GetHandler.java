package by.it.academy.adorop.web.infrastructure.http.method.handlers.get;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Map;

public interface GetHandler<ID extends Serializable> {
    ResponseEntity getBy(Map<String, String> properties);
    ResponseEntity get(ID id);
}
