package by.it.academy.adorop.web.infrastructure.http.method.handlers.get;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GetHandler<T> {
    ResponseEntity getBy(Map<String, Object> properties);
}
