package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveUserCommand<T extends User> extends BasicCommandVerifyingRequest {

    private static final Logger LOGGER = Logger.getLogger(SaveUserCommand.class);
    private final UserService<T> userService;

    public SaveUserCommand(HttpServletRequest request, UserService<T> userService) {
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
    protected void move(HttpServletResponse response) {

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
