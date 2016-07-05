package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginationContentPutter.class)
public class TeacherControllerTest {

    private static final Course MODEL_ATTRIBUTE_COURSE = new Course();
    private static final Teacher CURRENT_TEACHER = new Teacher();

    private TeacherController controller;
    @Mock
    private HttpServletRequest request;
    @Mock
    private CourseService courseService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private TeacherService teacherService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PaginationContentPutter.class);
        controller = new TeacherController(courseService, teacherService);
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

    @Test
    public void testAddCourse() throws Exception {
        assertEquals("course/add", controller.addCourse(model, MODEL_ATTRIBUTE_COURSE));
        verify(model).addAttribute("course", MODEL_ATTRIBUTE_COURSE);
    }

    @Test
    public void saveNewCourseShouldReturnOnAddCoursePage() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("course/add", controller.saveNewCourse(MODEL_ATTRIBUTE_COURSE, bindingResult, model, CURRENT_TEACHER));
    }

    @Test
    public void testSaveNewCourseOnPositiveScenario() throws Exception {
        assertEquals("redirect:/teachers", controller.saveNewCourse(MODEL_ATTRIBUTE_COURSE, bindingResult, model, CURRENT_TEACHER));
        verify(teacherService).addCourse(CURRENT_TEACHER, MODEL_ATTRIBUTE_COURSE);
    }
}