package by.it.academy.adorop.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class TeacherControllerTest {

    private TeacherController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new TeacherController();
    }

    @Test
    public void testMain() throws Exception {
        assertEquals("main/teachers", controller.showCourses());
    }
}