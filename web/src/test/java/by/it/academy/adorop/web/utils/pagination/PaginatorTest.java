package by.it.academy.adorop.web.utils.pagination;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class PaginatorTest {

    private Paginator paginator;
    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        paginator = new Paginator(request);
    }

    //TODO название срочно!!!!!
    @Test
    public void testGetPagesNumbersWhenCurrentPageIsOneAndLastPageIsGreaterThanRange() throws Exception {
        when_CurrentPage_And_MaxResult_Are(1, 3);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 10), paginator.getPagesNumbers(30L));
    }

    @Test
    public void testGetPagesNumbersWhenCurrentPageIsTwoAndLastPageIsLessThenRange() throws Exception {
        when_CurrentPage_And_MaxResult_Are(1, 3);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), paginator.getPagesNumbers(20L));
    }

    private void verifySetPagesNumbers(Integer... expectedPagesNumbers) {
        List<Integer> expected = Arrays.asList(expectedPagesNumbers);
        verify(request).setAttribute("numbersOfPages", expected);
    }

    @Test
    public void testGetPagesNumbersWhenCurrentPageIsInTheCenter() throws Exception {
        when_CurrentPage_And_MaxResult_Are(5, 2);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), paginator.getPagesNumbers(18L));
    }

    private void when_CurrentPage_And_MaxResult_Are(int currentPage, int maxResult) {
        paginator.setCurrentPage(currentPage);
        paginator.setMaxResult(maxResult);
    }
}