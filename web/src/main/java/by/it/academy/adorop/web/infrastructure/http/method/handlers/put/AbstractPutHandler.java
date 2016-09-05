package by.it.academy.adorop.web.infrastructure.http.method.handlers.put;

import by.it.academy.adorop.service.api.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.io.Serializable;

public abstract class AbstractPutHandler<T, ID extends Serializable> implements PutHandler<T>{
    @Override
    public ResponseEntity<Void> update(T entity, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        service().update(entity);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    protected abstract Service<T, ID> service();
}
