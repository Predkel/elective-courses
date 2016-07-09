package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
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

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginationContentPutter.class)
public class TeacherControllerTest {

    private static final Course SOME_COURSE = new Course();
    private static final Teacher CURRENT_TEACHER = new Teacher();
    private static final long ANY_LONG = 1L;
    private static final Mark SOME_MARK = new Mark();
    private static final int INT_ONE = 1;
    public static final int INT_ELEVEN = 11;

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
    @Mock
    private MarkService markService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PaginationContentPutter.class);
        controller = new TeacherController(courseService, teacherService, markService);
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
        assertEquals("course/add", controller.addCourse(model, SOME_COURSE));
        verify(model).addAttribute("course", SOME_COURSE);
    }

    @Test
    public void saveNewCourseShouldReturnOnAddCoursePage() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("course/add", controller.saveNewCourse(SOME_COURSE, bindingResult, model, CURRENT_TEACHER));
    }

    @Test
    public void testSaveNewCourseOnPositiveScenario() throws Exception {
        assertEquals("redirect:/teachers", controller.saveNewCourse(SOME_COURSE, bindingResult, model, CURRENT_TEACHER));
        verify(teacherService).addCourse(CURRENT_TEACHER, SOME_COURSE);
    }

    @Test
    public void testShowCourse() throws Exception {
        assertEquals("teachers/course", controller.showCourse(SOME_COURSE, model));
    }

    @Test
    public void showCourseShouldPutMarksByRequestedCourse() throws Exception {
        ArrayList<Mark> expectedMarks = new ArrayList<>();
        when(markService.getByCourse(anyObject())).thenReturn(expectedMarks);
        controller.showCourse(SOME_COURSE, model);
        verify(model).addAttribute("marks", expectedMarks);
    }

    @Test
    public void evaluateShouldReturnOnShowCoursePageWhenMarkValueIsNotNumberBetweenZeroAndTen() throws Exception {
        Long courseId = ANY_LONG;
        Integer markValue = INT_ELEVEN;
        assertEquals("teachers/course", controller.evaluate(SOME_MARK, courseId, markValue, model));
        verify(model).addAttribute("message", "Should be a number between zero and ten");
    }

        @Test
    public void testEvaluateOnPositiveScenario() throws Exception {
        assertEquals("redirect:/teachers/course?courseId=" + ANY_LONG, controller.evaluate(SOME_MARK, ANY_LONG, INT_ONE, model));
        verify(teacherService).evaluate(SOME_MARK);
    }
}