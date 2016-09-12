package by.it.academy.adorop.web.controllers;

import integration.by.it.academy.adorop.web.controllers.AbstractIntegrationTest;
import org.junit.Test;

import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedStudent;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeacherControllerTest extends AbstractIntegrationTest {
    @Test
    public void getTeachersMainShouldReturn403statusCodeWhenCurrentPrincipalIsNotTeacher() throws Exception {
        mvc.perform(get("/teachers")
                .with(authentication(authenticatedStudent())))
                .andExpect(status().is(403));
    }
}