package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import by.it.academy.adorop.web.utils.pagination.PaginatorBuilder;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginationContentPutter.class)
public class StudentControllerTest {

    private static final long ANY_LONG = 1L;
    private static final Student SOME_STUDENT = new Student();
    public static final Course SOME_COURSE = new Course();
    private StudentController controller;

    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;
    @Mock
    private MarkService markService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(PaginationContentPutter.class);
        MockitoAnnotations.initMocks(this);
        controller = new StudentController(courseService, studentService, markService);
    }

    @Test
    public void showCoursesShouldPutPaginationContent() throws Exception {
        controller.showCourses(request);
        PowerMockito.verifyStatic();
        PaginationContentPutter.putPaginationContent(request, courseService, "courses");
    }

    @Test
    public void testShowCourse() throws Exception {
        assertEquals("students/course", showCourse());
    }

    private String showCourse() {
        return controller.showCourse(model, SOME_STUDENT, SOME_COURSE);
    }

    @Test
    public void showCourseShouldPutCourse() throws Exception {
        Course expectedCourse = new Course();
        when(courseService.find(anyLong())).thenReturn(expectedCourse);
        showCourse();
        verify(model).addAttribute("course", expectedCourse);
    }

    @Test
    public void testShowCourseWhenStudentIsCourseListener() throws Exception {
        boolean isCourseListener = true;
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(isCourseListener);
        Mark expectedMark = getExpectedMark();
        showCourse();
        verify(model).addAttribute("isCourseListener", isCourseListener);
        verify(model).addAttribute("mark", expectedMark);
    }

    private Mark getExpectedMark() {
        Mark expectedMark = new Mark();
        when(markService.getByStudentAndCourse(anyObject(), anyObject())).thenReturn(expectedMark);
        return expectedMark;
    }

    @Test
    public void registerForTheCourseShouldRedirectToShowCourse() throws Exception {
        SOME_COURSE.setId(ANY_LONG);
        assertEquals("redirect:/students/course?courseId=" + ANY_LONG, controller.registerForTheCourse(SOME_COURSE, SOME_STUDENT));
    }

    @Test
    public void registerForTheCourseShouldRegisterStudentForTheCourse() throws Exception {
        Course requestedCourse = new Course();
        when(courseService.find(ANY_LONG)).thenReturn(requestedCourse);
        controller.registerForTheCourse(SOME_COURSE, SOME_STUDENT);
        verify(studentService).registerForTheCourse(SOME_STUDENT, requestedCourse);
    }

    @Test
    public void testRegister() throws Exception {
        assertEquals("register", controller.register(model));
    }

    @Test
    public void testSaveNewStudentWhenRequestIsValid() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(studentService.isAlreadyExists(anyString())).thenReturn(false);
        assertEquals("redirect:/students", controller.saveNewStudent(SOME_STUDENT, bindingResult, model));
        verify(studentService).persist(SOME_STUDENT);
    }

    @Test
    public void testSaveNewStudentWhenRequestIsNotValid() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("register", controller.saveNewStudent(SOME_STUDENT, bindingResult, model));
    }

    @Test
    public void testSaveNewStudentWhenUserAlreadyExists() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(studentService.isAlreadyExists(anyString())).thenReturn(true);
        assertEquals("register", controller.saveNewStudent(SOME_STUDENT, bindingResult, model));
    }
}