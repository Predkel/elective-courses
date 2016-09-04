package integration.by.it.academy.adorop.web.controllers;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedStudent;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseControllerTest extends AbstractIntegrationTest {
    @Test
    public void getBunchShouldReturn400statusCodeWhenOneOfParametersIsNegativeNumber() throws Exception {
        performGetBunchWith("-1", "10")
                .andExpect(status().is(400));
    }

    @Test
    public void getBunchShouldReturnCoursesFromDatabaseWhenRequestIsValid() throws Exception {
        performGetBunchWith("1", "10")
                .andExpect(jsonPath("$.[0].teacher.documentId").value("adorop"))
                .andExpect(jsonPath("$.[2].title").value("title_4"));
    }

    private ResultActions performGetBunchWith(String firstResult, String maxResults) throws Exception {
        return mvc.perform((get("/courses").with(authentication(authenticatedStudent()))
                .param("firstResult", firstResult)
                .param("maxResults", maxResults)));
    }
}