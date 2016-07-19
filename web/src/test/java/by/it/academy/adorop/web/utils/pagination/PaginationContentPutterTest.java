package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PaginatorBuilder.class)
public class PaginationContentPutterTest {
    public static final String NAME_OF_COURSES_ATTRIBUTE = "courses";
    @Mock
    HttpServletRequest request;
    @Mock
    Service service;
    @Mock
    private PaginatorBuilder paginatorBuilder;
    @Mock
    private Paginator paginator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(PaginatorBuilder.class);
    }

    @Test
    public void shouldPutCoursesAndPagesNumbersWhenContentIsCourses() throws Exception {
        List<Integer> expectedPagesNumbers = getExpectedPagesNumbers();
        List<Course> expectedCourses = new ArrayList<>();
        setExpectedEntities(expectedCourses);
        PaginationContentPutter.putPaginationContent(request, service, NAME_OF_COURSES_ATTRIBUTE);
        verify(request).setAttribute("numbersOfPages", expectedPagesNumbers);
        verify(request).setAttribute("courses", expectedCourses);
    }

    private void setExpectedEntities(List expectedEntities) {
        when(service.getBunch(anyInt(), anyInt())).thenReturn(expectedEntities);
    }

    private List<Integer> getExpectedPagesNumbers() {
        PowerMockito.when(PaginatorBuilder.newInstance(request)).thenReturn(paginatorBuilder);
        when(paginatorBuilder.buildPaginator()).thenReturn(paginator);
        List<Integer> expectedPagesNumbers = new ArrayList<>();
        when(paginator.getPagesNumbers(anyLong())).thenReturn(expectedPagesNumbers);
        return expectedPagesNumbers;
    }
}