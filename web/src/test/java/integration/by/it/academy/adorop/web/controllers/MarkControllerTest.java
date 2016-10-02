package integration.by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.MarkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.ResultActions;

import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedStudent;
import static integration.by.it.academy.adorop.web.controllers.AuthenticationUtilsForTests.authenticatedTeacher;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MarkControllerTest extends AbstractIntegrationTest {


    @Test
    public void createNewShouldReturn201StatusCodeAndSaveNewMarkInDatabaseWhenRequestIsValid() throws Exception {
        Course existingCourse = new Course();
        existingCourse.setId(12L);
        Student principal = (Student) authenticatedStudent().getPrincipal();
        performCreateNewMarkWith(existingCourse, principal)
                .andExpect(status().is(201));
    }

    @Test
    public void createNewShouldReturn400statusCodeWhenCourseOfGivenMarkDoesNotExistInDatabase() throws Exception {
        Course notExistingCourse = new Course();
        notExistingCourse.setId(500L);
        performCreateNewMarkWith(notExistingCourse, (Student) authenticatedStudent().getPrincipal())
                .andExpect(status().is(400));
    }

    @Test
    public void createNewShouldReturn403statusCodeWhenStudentOfGivenMarkIsNotCurrentlyAuthenticatedPrincipal() throws Exception {
        Student someStudent = new Student();
        someStudent.setId(10003L);
        performCreateNewMarkWith(null, someStudent).andExpect(status().is(403));
    }

    private String jsonMark(Mark mark) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper();
        return mapper.writeValueAsString(mark);
    }

    private ResultActions performCreateNewMarkWith(Course course, Student student) throws Exception {
        Mark mark = new Mark(student, course);
        String content = jsonMark(mark);
        System.out.println(content);
        return mvc.perform(post("/marks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authenticatedStudent()))
                .content(content));

    }

    private ResultActions performGetBySecondCourseWith(Authentication authentication) throws Exception {
        return mvc.perform(get("/marks?courseId=2")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authentication)));
    }

    @Test
    public void getByCourseShouldReturnJsonListWhenRequestIsValid() throws Exception {
        performGetBySecondCourseWith(authenticatedTeacher())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].student.firstName").value("wayne "))
                .andExpect(jsonPath("$.[1].value").value(1));;

    }

    @Test
    public void getByCourseShouldReturn403statusCodeWhenCurrentPrincipalIsNotTeacherOfTheCourse() throws Exception {
        performGetBySecondCourseWith(authenticatedStudent())
                .andExpect(status().is(403));
    }

    @Test
    public void getByCourseAndStudentShouldReturnMarkFromDatabaseWhenRequestIsValid() throws Exception {
        performGetBySecondCourseAnd("10002").andExpect(jsonPath("$.id").value(10005));
    }

    @Test
    public void getByCourseAndStudentShouldReturn403statusCodeWhenGivenStudentIsNotCurrentPrincipal() throws Exception {
        performGetBySecondCourseAnd("10003").andExpect(status().is(403));
    }

    @Test
    public void getByCourseAndStudentShouldReturnNullWhenRequestedMarkDoesNotExist() throws Exception {
        mvc.perform(get("/marks?courseId=9&studentId=10002")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authenticatedStudent())))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$").isEmpty());
    }

    private ResultActions performGetBySecondCourseAnd(String studentId) throws Exception {
        return mvc.perform(get("/marks")
                .param("courseId", "2").param("studentId", studentId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(authenticatedStudent())));
    }

    @Test
    public void updateShouldReturn200statusCodeWhenRequestIsValid() throws Exception {
        Mark validMark = buildMark(2L, 10002L, 10005L, 10);
        performUpdateWith(validMark).andExpect(status().is(200));
    }

    @Test
    public void updateShouldReturn4xxStatusCodeWhenOneOfRequiredPropertiesIsNull() throws Exception {
        Mark markWithNullCourseId = buildMark(null, 10002L, 10005L, 10);
        performUpdateWith(markWithNullCourseId).andExpect(status().is4xxClientError());
    }

    @Test
    public void updateShouldReturn403statusCodeWhenCurrentPrincipalIsNotTeacherOfCourse() throws Exception {
        Mark foreignForCurrentPrincipal = buildMark(14L, 10002L, 1L, 10);
        performUpdateWith(foreignForCurrentPrincipal).andExpect(status().is(403));
    }

    @Test
    public void updateShouldReturn400statusCodeWhenMarkConsistsFromInvalidCombinationOfIds() throws Exception {
        Mark invalidMark = buildMark(2L, 10003L, 1L, 10);
        performUpdateWith(invalidMark).andExpect(status().is(400));
    }

    private ResultActions performUpdateWith(Mark mark) throws Exception {
        return mvc.perform(put("/marks")
                .with(authentication(authenticatedTeacher()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonMark(mark)));
    }

    private Mark buildMark(Long courseId, Long studentId, Long markId, Integer markValue) {
        Course course = new Course();
        course.setId(courseId);
        Student student = new Student();
        student.setId(studentId);
        Mark mark = new Mark(student, course);
        mark.setId(markId);
        mark.setValue(markValue);
        return mark;
    }
}