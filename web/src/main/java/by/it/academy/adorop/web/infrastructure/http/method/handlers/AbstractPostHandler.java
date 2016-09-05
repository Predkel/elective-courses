package by.it.academy.adorop.web.infrastructure.http.method.handlers;

import by.it.academy.adorop.service.api.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.io.Serializable;

public abstract class AbstractPostHandler<T, ID extends Serializable> implements PostHandler<T> {

    @Override
    public ResponseEntity createNew(T entity, Errors errors) {
        if (errors.hasErrors()) {
            return withoutBody(HttpStatus.BAD_REQUEST);
        }
        if (alreadyExists(entity)) {
            return withoutBody(HttpStatus.CONFLICT);
        }
        service().persist(entity);
        return withoutBody(HttpStatus.CREATED);
    }

    private ResponseEntity withoutBody(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }

    private boolean alreadyExists(T entity) {
        return service().isAlreadyExists(entity);
    }

    protected abstract Service<T, ID> service();
}
