package by.it.academy.adorop.web.security;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.web.security.authentication.AuthenticationSuccessHandlerImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationSuccessHandlerImplTest {

    AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    Authentication authentication;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        authenticationSuccessHandler = new AuthenticationSuccessHandlerImpl();
    }

    @Test
    public void shouldRedirectToTeachersWhenPrincipalIsTeacher() throws Exception {
        shouldRedirectTo_WhenPrincipalIs(new Teacher(), "/teachers");
    }
    @Test
    public void shouldRedirectToStudentsWhenPrincipalIsStudent() throws Exception {
        shouldRedirectTo_WhenPrincipalIs(new Student(), "/students");
    }



    private void shouldRedirectTo_WhenPrincipalIs(Object currentPrincipal, String expectedPathToController) throws IOException, ServletException {
        when(authentication.getPrincipal()).thenReturn(currentPrincipal);
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        verify(response).sendRedirect(expectedPathToController);
    }
}