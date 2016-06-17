package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationCommand<T extends User> extends Command {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationCommand.class);
    static final String AUTHENTICATION_PAGE = "views/authentication.jsp";
    static final String FAILED_ATTEMPT_MESSAGE = "Invalid combination of login and password";

    private final UserService<T> userService;

    public AuthenticationCommand(HttpServletRequest request, UserService<T> userService) {
        super(request);
        this.userService = userService;
    }


    @Override
    protected boolean requestIsValid() {
        return false;
    }

    @Override
    protected void setContent() {

    }

    @Override
    protected void goFurther(HttpServletResponse response) {

    }

    @Override
    protected void setExplainingMessage() {

    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) {

    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
