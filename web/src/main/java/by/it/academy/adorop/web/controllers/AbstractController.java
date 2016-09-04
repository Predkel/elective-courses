package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.Serializable;

public abstract class AbstractController<T, ID extends Serializable> {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createNew(@Valid T entity, Errors errors) {
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
