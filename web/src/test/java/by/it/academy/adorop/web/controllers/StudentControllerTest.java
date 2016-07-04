package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.utils.pagination.Paginator;
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

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginatorBuilder.class)
public class StudentControllerTest {

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
    private PaginatorBuilder paginatorBuilder;
    @Mock
    private Paginator paginator;
    @Mock
    private Model model;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(PaginatorBuilder.class);
        MockitoAnnotations.initMocks(this);
        controller = new StudentController(courseService, studentService, markService);
    }

    @Test
    public void mainShouldPutCoursesAndPagesNumbers() throws Exception {
        List<Integer> expectedPagesNumbers = getExpectedPagesNumbers();
        ArrayList<Course> expectedCourses = getExpectedCourses();
        controller.main(model, request);
        verify(model).addAttribute("numbersOfPages", expectedPagesNumbers);
        verify(model).addAttribute("courses", expectedCourses);
    }

    private ArrayList<Course> getExpectedCourses() {
        ArrayList<Course> expectedCourses = new ArrayList<>();
        when(courseService.getBunch(anyInt(), anyInt())).thenReturn(expectedCourses);
        return expectedCourses;
    }

    private List<Integer> getExpectedPagesNumbers() {
        PowerMockito.when(PaginatorBuilder.newInstance(request)).thenReturn(paginatorBuilder);
        when(paginatorBuilder.buildPaginator()).thenReturn(paginator);
        List<Integer> expectedPagesNumbers = new ArrayList<>();
        when(paginator.getPagesNumbers(anyLong())).thenReturn(expectedPagesNumbers);
        return expectedPagesNumbers;
    }

    @Test
    public void testLogin() throws Exception {
        assertEquals("login", controller.login());
    }

    @Test
    public void testShowCourse() throws Exception {

        assertEquals("course/student", showCourse());
    }

    private String showCourse() {
        Student student = new Student();
        return controller.showCourse(model, student, 1L);
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
        isStudentCourseListener(isCourseListener);
        Mark expectedMark = new Mark();
        when(markService.getByStudentAndCourse(anyObject(), anyObject())).thenReturn(expectedMark);
        showCourse();
        verify(model).addAttribute("isCourseListener", isCourseListener);
        verify(model).addAttribute("mark", expectedMark);
    }

    private void isStudentCourseListener(boolean isCourseListener) {
        when(studentService.isCourseListener(anyObject(), anyObject())).thenReturn(isCourseListener);
    }
}