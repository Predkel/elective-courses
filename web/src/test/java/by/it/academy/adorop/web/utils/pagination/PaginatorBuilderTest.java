package by.it.academy.adorop.web.utils.pagination;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaginatorBuilderTest {

    public static final String ANY_STRING = "anyString";
    PaginatorBuilder builder;
    @Mock
    private Paginator paginator;
    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuildPaginatorWhenMaxResultParameterIsNotNumericString() throws Exception {
        when_MaxResult_PageNumber_And_FirstResult_ParametersAre("abc", ANY_STRING, ANY_STRING);

        builder.buildPaginator();

        int expected = PaginatorBuilder.DEFAULT_MAX_RESULT;
        shouldAssignMaxResultValue(expected);
        shouldSetToRequest("maxResult", expected);
    }

    @Test
    public void testBuildPaginatorWhenMaxResultParameterIsValid() throws Exception {
        when_MaxResult_PageNumber_And_FirstResult_ParametersAre("20", ANY_STRING, ANY_STRING);

        builder.buildPaginator();

        int expected = 20;
        shouldAssignMaxResultValue(expected);
        shouldSetToRequest("maxResult", expected);
    }

    private void shouldAssignMaxResultValue(int value) {
        verify(paginator).setMaxResult(value);
    }

    @Test
    public void testBuildPaginatorWhenGoalOfRequestIsToChangeMaxResult() throws Exception {
        when_MaxResult_PageNumber_And_FirstResult_ParametersAre("5", null, "20");

        builder.buildPaginator();

        int expectedFirstResult = 20;
        int expectedCurrentPage = 4;

        shouldProcessFirstResult(expectedFirstResult);
        shouldProcessCurrentPage(expectedCurrentPage);
    }

    @Test
    public void testBuildPaginatorWhenGoalOfRequestIsToChangePage() throws Exception {
        when_MaxResult_PageNumber_And_FirstResult_ParametersAre("5", "3", null);

        builder.buildPaginator();

        int expectedFirstResult = 10;
        int expectedCurrentPage = 3;

        shouldProcessFirstResult(expectedFirstResult);

        shouldProcessCurrentPage(expectedCurrentPage);
    }

    private void shouldProcessCurrentPage(int expectedCurrentPage) {
        shouldAssignCurrentPageValue(expectedCurrentPage);
        shouldSetToRequest("currentPage", expectedCurrentPage);
    }

    private void shouldProcessFirstResult(int expectedFirstResult) {
        shouldAssignFirstResultValue(expectedFirstResult);
        shouldSetToRequest("firstResult", expectedFirstResult);
    }

    @Test
    public void testBuildPaginatorWhenRequestIsFirstTimeOrNotValid() throws Exception {
        when_MaxResult_PageNumber_And_FirstResult_ParametersAre(ANY_STRING, "", "");

        builder.buildPaginator();

        int expectedFirstResult = PaginatorBuilder.DEFAULT_FIRST_RESULT;
        int expectedCurrentPage = PaginatorBuilder.DEFAULT_PAGE_NUMBER;

        shouldProcessFirstResult(expectedFirstResult);
        shouldProcessCurrentPage(expectedCurrentPage);
    }

    private void shouldAssignCurrentPageValue(int expectedCurrentPage) {
        verify(paginator).setCurrentPage(expectedCurrentPage);
    }

    private void shouldAssignFirstResultValue(int expected) {
        verify(paginator).setFirstResult(expected);
    }



    private void shouldSetToRequest(String nameOfAttribute, int valueOfAttribute) {
        verify(request).setAttribute(nameOfAttribute, valueOfAttribute);
    }

    private void when_MaxResult_PageNumber_And_FirstResult_ParametersAre(String maxResult, String pageNumber, String firstResult) {
        when(request.getParameter("maxResult")).thenReturn(maxResult);
        when(request.getParameter("pageNumber")).thenReturn(pageNumber);
        when(request.getParameter("firstResult")).thenReturn(firstResult);
        builder = new PaginatorBuilder(request, paginator);
    }

    @Test
    public void testName() throws Exception {
        double x = 26;
        double y = 5;

        assertSame(6, (int) Math.ceil(x / y));

    }
}