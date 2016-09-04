package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

public abstract class AbstractUserController<U extends User> {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createNew(@Valid U user, Errors errors) {
        if (errors.hasErrors()) {
            return withoutBody(HttpStatus.BAD_REQUEST);
        }
        if (alreadyExists(user)) {
            return withoutBody(HttpStatus.CONFLICT);
        }
        service().persist(user);
        return withoutBody(HttpStatus.CREATED);
    }

    private ResponseEntity withoutBody(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }

    private boolean alreadyExists(U user) {
        return service().isAlreadyExists(user);
    }

    protected abstract UserService<U> service();

    @RequestMapping(value = "/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody U getCurrent(@AuthenticationPrincipal U user) {
        user.setPassword(null);
        return user;
    }
}
