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

import static by.it.academy.adorop.web.utils.Constants.*;

public class SaveUserCommand<T extends User> extends BasicCommandVerifyingRequest {

    private final UserService<T> userService;
    private final String documentIdParameter;
    private final String passwordParameter;
    private final String firstNameParameter;
    private final String lastNameParameter;

    SaveUserCommand(HttpServletRequest request, UserService<T> userService) {
        super(request);
        this.userService = userService;
        documentIdParameter = request.getParameter("documentId");
        passwordParameter = request.getParameter("password");
        firstNameParameter = request.getParameter("firstName");
        lastNameParameter = request.getParameter("lastName");
    }

    @Override
    protected boolean requestIsValid() throws ServiceException {
        return !isOneOfParametersEmpty()
                && !userService.isAlreadyExists(documentIdParameter);
    }

    private boolean isOneOfParametersEmpty() {
        return RequestParamValidator.hasEmpty(documentIdParameter, passwordParameter, firstNameParameter, lastNameParameter);
    }

    @Override
    protected void setExplainingMessage() {
        String message = isOneOfParametersEmpty() ? FIELDS_SHOULD_BE_NOT_EMPTY_MESSAGE : USER_ALREADY_EXISTS_MESSAGE;
        request.setAttribute("message", message);
    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(REGISTRATION_PAGE, request, response);
    }

    @Override
    protected void prepareResponse() throws ServiceException {
        T newUser = buildNewUserFromRequest();
        T persistedUser = userService.persist(newUser);
        setupSession(persistedUser);
    }

    private T buildNewUserFromRequest() {
        T user;
        if (isRequestFromStudent()) {
            user = (T) new Student();
        } else {
            user = (T) new Teacher();
        }
        user.setDocumentId(documentIdParameter);
        user.setPassword(passwordParameter);
        user.setFirstName(firstNameParameter);
        user.setLastName(lastNameParameter);
        return user;
    }

    private boolean isRequestFromStudent() {
        return request.getServletPath().equals("/students");
    }

    private void setupSession(T persistedUser) {
        request.getSession().setAttribute(defineNameOfAttribute(persistedUser), persistedUser);
    }

    private String defineNameOfAttribute(T persistedUser) {
        return persistedUser.getClass().getSimpleName().toLowerCase();
    }

    @Override
    protected void move(HttpServletResponse response) throws IOException {
        String pathToMain = PathBuilder.buildPathToMain(request);
        response.sendRedirect(pathToMain);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(SaveUserCommand.class);
    }
}
