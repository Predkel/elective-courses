package integration.by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.ResultActions;

import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedStudent;
import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedTeacher;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseControllerTest extends AbstractIntegrationTest {
    @Autowired
    private CourseService courseService;

    @Test
    public void getCountShouldReturnTheSameResultThatServiceInJson() throws Exception {
        mvc.perform(get("/courses/count")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authenticatedTeacher())))
                .andExpect(jsonPath("$.count").value(courseService.getTotalCount().intValue()));
    }

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
        return mvc.perform(get("/courses").with(authentication(authenticatedStudent()))
                .param("firstResult", firstResult)
                .param("maxResults", maxResults));
    }

    @Test
    public void createNewShouldReturn403statusCodeWhenStudentAttemptsToCreateCourse() throws Exception {
        performCreateNewCourseWith(authenticatedStudent(), "anyTitle", new Teacher())
                .andExpect(status().is(403));

    }

    @Test
    public void createNewShouldReturn201statusCodeAndPersistCourseWhenRequestIsValid() throws Exception {
        String newTitle = "newTitle";
        performCreateNewWithValidAuthenticationAnd(newTitle)
                .andExpect(status().is(201));
        assertNotNull(courseService.getSingleResultBy("title", newTitle));
    }

    @Test
    public void createNewShouldReturn400statusCodeWhenPassedCourseIsNotValid() throws Exception {
        String emptyString = "";
        performCreateNewWithValidAuthenticationAnd(emptyString)
                .andExpect(status().is(400));
    }

    @Test
    public void createNewShouldReturn409statusCodeWhenGivenCourseAlreadyExists() throws Exception {
        String existingTitle = "title_0";
        performCreateNewWithValidAuthenticationAnd(existingTitle)
                .andExpect(status().is(409));
    }

    private String jsonCourseWith(String title, Teacher teacher) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Course course = new Course();
        course.setTitle(title);
        course.setTeacher(teacher);
        return mapper.writeValueAsString(course);
    }

    private ResultActions performCreateNewCourseWith(Authentication authentication, String title, Teacher teacher) throws Exception {
        return mvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authentication))
                .content(jsonCourseWith(title, teacher)));
    }

    private ResultActions performCreateNewWithValidAuthenticationAnd(String title) throws Exception {
        Authentication authentication = authenticatedTeacher();
        Teacher authenticatedTeacher = (Teacher) authentication.getPrincipal();
        return performCreateNewCourseWith(authentication, title, authenticatedTeacher);
    }
}