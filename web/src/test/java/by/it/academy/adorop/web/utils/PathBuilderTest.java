package by.it.academy.adorop.web.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class PathBuilderTest {

    private static final String STUDENTS_PATH = "/students";
    private static final String PATH_TO_STUDENTS_MAIN = "/students?operation=main";
    private static final String SHOW_COURSE_COURSE_PATH = "/students?operation=showCourse&courseId=1";

    @Mock
    HttpServletRequest request;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(request.getServletPath()).thenReturn(STUDENTS_PATH);
    }

    @Test
    public void testBuildPath() throws Exception {
        assertEquals(PATH_TO_STUDENTS_MAIN, PathBuilder.buildPath(request, "main"));
    }

    @Test
    public void testBuildPathWithParameters() throws Exception {
        assertEquals(SHOW_COURSE_COURSE_PATH,
                PathBuilder.buildPath(request, "showCourse", "courseId", "1"));
    }
}