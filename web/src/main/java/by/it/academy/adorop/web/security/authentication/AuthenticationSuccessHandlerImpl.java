package by.it.academy.adorop.web.security.authentication;

import by.it.academy.adorop.model.users.Teacher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        String pathToController;
        if (principal.getClass() == Teacher.class) {
            pathToController = "/teachers";
        } else {
            pathToController = "/students";
        }
        response.sendRedirect(pathToController);
    }
}
