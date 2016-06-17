package by.it.academy.adorop.web.commands;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.*;

public class AuthenticationCommand<T extends User> extends Command {

    private final UserService<T> userService;
    private final String documentIdParameter;
    private final String passwordParameter;

    public AuthenticationCommand(HttpServletRequest request, UserService<T> userService) {
        super(request);
        this.userService = userService;
        documentIdParameter = request.getParameter("documentId");
        passwordParameter = request.getParameter("password");
    }


    @Override
    protected boolean requestIsValid() throws ServiceException {
        return !RequestParamValidator.areEmpty(documentIdParameter, passwordParameter)
                && userService.isValid(documentIdParameter, passwordParameter);
    }

    @Override
    protected void setContent() throws ServiceException {
        T validUser = userService.getByDocumentId(documentIdParameter);
        String nameOfAttribute = defineNameOfAttribute(validUser);
        getSession().setAttribute(nameOfAttribute, validUser);
    }

    private String defineNameOfAttribute(T user) {
        return user.getClass().getSimpleName().toLowerCase();
    }

    private HttpSession getSession() {
        return request.getSession();
    }

    @Override
    protected void goFurther(HttpServletResponse response) throws IOException {
        String pathToMain = PathBuilder.buildPath(request, OPERATION_MAIN);
        response.sendRedirect(pathToMain);
    }

    @Override
    protected void setExplainingMessage() {
        String explainingMessage;
        if (RequestParamValidator.areEmpty(documentIdParameter, passwordParameter)) {
            explainingMessage = SHOULD_BE_NOT_EMPTY_MESSAGE;
        } else {
            explainingMessage = INVALID_USER_MESSAGE;
        }
        request.setAttribute("message", explainingMessage);
    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(AUTHENTICATION_PAGE, request, response);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(AuthenticationCommand.class);
    }
}
