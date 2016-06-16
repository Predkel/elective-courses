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

public class AuthenticationCommand<T extends User> implements Command {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationCommand.class);
    static final String AUTHENTICATION_PAGE = "views/authentication.jsp";
    static final String FAILED_ATTEMPT_MESSAGE = "Invalid combination of login and password";

    private final UserService<T> userService;

    AuthenticationCommand(UserService<T> userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String documentId = request.getParameter("documentId");
        String password = request.getParameter("password");
        try {
            if (RequestParamValidator.areEmpty(documentId, password)) {
                sendToAuthenticationPage(request, response);
            } else {
                if (userService.isValid(documentId, password)) {
                    T validUser = userService.getByDocumentId(documentId);
                    request.getSession().setAttribute(buildNameOfUserForSessionAttribute(validUser), validUser);
                    response.sendRedirect(buildPathToMain(request));
                } else {
                    request.setAttribute("failedAttemptMessage", FAILED_ATTEMPT_MESSAGE);
                    sendToAuthenticationPage(request, response);
                }
            }
        } catch (ServletException | IOException | ServiceException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private String buildNameOfUserForSessionAttribute(T user) {
        return user.getClass().getSimpleName().toLowerCase();
    }

    private void sendToAuthenticationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(AUTHENTICATION_PAGE).forward(request, response);
    }

    private String buildPathToMain(HttpServletRequest request) {
        return request.getServletPath() + "?operation=main";
    }
}
