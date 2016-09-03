package integration.by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.web.security.authentication.UserAuthentication;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest extends AbstractIntegrationTest {

    private static final String VALID_STRING = "validString";
    private static final String CREATE_STUDENT_URL = "/students";
    private static final String GET_CURRENT_STUDENT_URL = "/students/current";
    private static final String EXISTING_DOCUMENT_ID = "rooney";
    private static final String NOT_VALID_STRING = "";

    @Test
    public void getCurrentShouldReturnAuthenticatedStudent() throws Exception {
        mvc.perform(get(GET_CURRENT_STUDENT_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .with(authentication(studentAuthentication())))
                .andExpect(jsonPath("$.documentId").value("adorop88"));
    }

    private Authentication studentAuthentication() {
        Student student = buildStudent();
        UserAuthentication<Student> authentication = UserAuthentication.newInstance(student);
        authentication.setAuthenticated(true);
        return authentication;
    }

    private Student buildStudent() {
        Student student = new Student();
        student.setId(10002L);
        student.setDocumentId("adorop88");
        student.setPassword("1234");
        student.setFirstName("1stName");
        student.setLastName("lastName");
        return student;
    }

    @Test
    public void createNewShouldReturn400codeWhenRequestIsNotValid() throws Exception {
        performCreateNewStudentWithParameters(VALID_STRING, NOT_VALID_STRING, VALID_STRING, VALID_STRING)
                .andExpect(status().is(400));
    }

    @Test
    public void createNewShouldReturn201statusCodeWhenRequestIsValid() throws Exception {
        performCreateNewStudentWithParameters(VALID_STRING, VALID_STRING, VALID_STRING, VALID_STRING)
                .andExpect(status().is(201));
    }

    @Test
    public void createNewShouldReturn409statusCodeWhenUserWithGivenDocumentIdAlreadyExists() throws Exception {
        performCreateNewStudentWithParameters(EXISTING_DOCUMENT_ID, VALID_STRING, VALID_STRING, VALID_STRING)
                .andExpect(status().is(409));
    }

    private ResultActions performCreateNewStudentWithParameters(String documentId, String password, String firstName, String lastname) throws Exception {
        return mvc.perform(post(CREATE_STUDENT_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("documentId", documentId)
                .param("password", password)
                .param("firstName", firstName)
                .param("lastName", lastname));
    }
}