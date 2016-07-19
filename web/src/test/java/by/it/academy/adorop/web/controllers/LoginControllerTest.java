package by.it.academy.adorop.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

public class LoginControllerTest {

    private LoginController loginController;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        loginController = new LoginController();
    }

    @Test
    public void testLogin() throws Exception {
        assertEquals("login", loginController.login(request, model));
    }
}