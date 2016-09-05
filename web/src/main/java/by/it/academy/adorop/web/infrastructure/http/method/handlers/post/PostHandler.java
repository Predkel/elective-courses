package by.it.academy.adorop.web.infrastructure.http.method.handlers.post;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public interface PostHandler<T> {
    ResponseEntity createNew(T entity, Errors errors);
}
