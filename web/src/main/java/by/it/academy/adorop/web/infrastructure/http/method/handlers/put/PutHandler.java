package by.it.academy.adorop.web.infrastructure.http.method.handlers.put;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public interface PutHandler<T> {
    ResponseEntity<Void> update(T entity, Errors errors);
}
