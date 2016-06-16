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

public class SaveUserCommand<T extends User> implements Command {

    private static final Logger LOGGER = Logger.getLogger(SaveUserCommand.class);
    private final UserService<T> userService;

    SaveUserCommand(UserService<T> userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String documentId = request.getParameter("documentId");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        try {
            if (requestIsNotValid(documentId, password)) {
                String message = defineMessage(documentId, password);
                Dispatcher.forwardWithMessage(REGISTER_FORM, request, response, message);
            } else {
                T newUser = buildUser(documentId, password, firstName, lastName, request);
                setupSession(newUser, request);
                goToMain(request, response);
            }
        } catch (ServletException | ServiceException | IOException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private void goToMain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathToMain = PathBuilder.buildPath(request, OPERATION_MAIN);
        response.sendRedirect(pathToMain);
    }

    private void setupSession(T newUser, HttpServletRequest request) throws ServiceException {
        String nameForAttribute = defineNameForAttribute(newUser);
        T savedUser = userService.save(newUser);
        request.getSession().setAttribute(nameForAttribute, savedUser);
    }

    private String defineNameForAttribute(T user) {
        return user.getClass().getSimpleName().toLowerCase();
    }

    @SuppressWarnings("unchecked")
    private T buildUser(String documentId, String password, String firstName, String lastName, HttpServletRequest request) {
        T user;
        if (request.getServletPath().equals("/students")) {
            user = (T) new Student();
        } else {
            user = (T) new Teacher();
        }
        user.setDocumentId(documentId);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    private String defineMessage(String documentId, String password) {
        return RequestParamValidator.areEmpty(documentId, password) ? SHOULD_BE_NOT_EMPTY_MESSAGE :
                                                                        USER_ALREADY_EXISTS_MESSAGE;
    }

    private boolean requestIsNotValid(String documentId, String password) throws ServiceException {
        return RequestParamValidator.areEmpty(documentId, password) || userService.isAlreadyExists(documentId);
    }
}
