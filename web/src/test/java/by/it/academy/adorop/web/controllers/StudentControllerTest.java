package by.it.academy.adorop.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = WebConfig.class)
public class StudentControllerTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new StudentController())
                .build();
    }

    @Test
    public void testGetStudentPrincipal() throws Exception {
        mvc.perform(get("/students/currentPrincipal").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}