package integration.by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;

import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedStudent;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MarkControllerTest extends AbstractIntegrationTest {

    @Test
    public void createNewShouldReturn201StatusCodeAndSaveNewMarkInDatabaseWhenRequestIsValid() throws Exception {
        Course existingCourse = new Course();
        existingCourse.setId(12L);
        Authentication authentication = authenticatedStudent();
        Student principal = (Student) authentication.getPrincipal();
        mvc.perform(post("/marks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authentication))
                .content(jsonMark(principal, existingCourse)))
                .andExpect(status().is(201));

    }

    private String jsonMark(Student student, Course course) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper();
        Mark mark = new Mark(student, course);
        String s = mapper.writeValueAsString(mark);
        System.out.println(s);
        return s;
    }
}