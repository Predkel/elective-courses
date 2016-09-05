package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class AbstractUserController<U extends User> {

    @RequestMapping(value = "/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody U getCurrent(@AuthenticationPrincipal U user) {
        user.setPassword(null);
        return user;
    }
}
