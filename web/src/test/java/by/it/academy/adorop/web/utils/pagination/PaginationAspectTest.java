package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.service.api.CourseService;
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
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginationContentPutter.class)
public class PaginationAspectTest {

    private PaginationAspect paginationAspect;
    @Mock
    private HttpServletRequest request;
    @Mock
    private CourseService courseService;
    @Mock
    Pagination annotation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PaginationContentPutter.class);
        paginationAspect = new PaginationAspect(courseService);
    }

    @Test
    public void testPutPaginationContent() throws Exception {
        String expectedBunchAttributeName = "courses";
        when(annotation.bunchAttributeName()).thenReturn(expectedBunchAttributeName);
        paginationAspect.putPaginationContent(annotation, request);
        PowerMockito.verifyStatic();
        PaginationContentPutter.putPaginationContent(request, courseService, expectedBunchAttributeName);
    }
}