package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginationContentPutter.class)
public class TeacherControllerTest {

    private TeacherController controller;

    @Mock
    private HttpServletRequest request;
    @Mock
    private CourseService courseService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PaginationContentPutter.class);
        controller = new TeacherController(courseService);
    }

    @Test
    public void testShowCourses() throws Exception {
        assertEquals("main/teachers", controller.showCourses(request));
    }

    @Test
    public void showCoursesShouldPutPaginationContent() throws Exception {
        controller.showCourses(request);
        PowerMockito.verifyStatic();
        PaginationContentPutter.putPaginationContent(request, courseService, "courses");
    }
}