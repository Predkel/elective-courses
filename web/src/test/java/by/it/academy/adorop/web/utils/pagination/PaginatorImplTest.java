package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestParamValidator.class, FormulasManager.class})
public class PaginatorImplTest {

    private static final int TEN_INT = 10;
    private static final String ENTITY_NAME = "entityName";
    private static final String TEN_STRING = "10";
    private static final long TEN_LONG = 10L;

    private PaginatorImpl paginator;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Formula formula;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        paginator = new PaginatorImpl(ENTITY_NAME, request);
        paginator.setTotalNumberOfEntities(TEN_LONG);
        PowerMockito.mockStatic(RequestParamValidator.class, FormulasManager.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void defineMaxResultShouldReturnAndSaveDefaultMaxResultWhenMaxResultParameterIsNotPositiveInt() throws Exception {
        PowerMockito.when(RequestParamValidator.isPositiveInt(anyString())).thenReturn(false);
        assertSame(PaginatorImpl.DEFAULT_MAX_RESULT, paginator.defineMaxResult());
        verify(request).setAttribute("maxResult", PaginatorImpl.DEFAULT_MAX_RESULT);
    }

    @Test
    public void defineMaxResultShouldReadAndSaveMaxResult() throws Exception {
        when(request.getParameter(anyString())).thenReturn(TEN_STRING);
        assertSame(TEN_INT, paginator.defineMaxResult());
        verify(request).setAttribute("maxResult", TEN_INT);
    }

    @Test
    public void defineFirstResultShouldReadPreviouslyWatchedLastResultAndMaxResultAndPassItsValuesToFormula() {
        when(session.getAttribute(ENTITY_NAME)).thenReturn(TEN_INT);
        when(request.getParameter("maxResult")).thenReturn(TEN_STRING);
        PowerMockito.when(FormulasManager.defineFormula(request)).thenReturn(formula);
        when(formula.calculateFirstResult(anyInt(), anyInt())).thenReturn(TEN_INT);
        assertSame(TEN_INT, paginator.defineFirstResult());
        verify(formula).calculateFirstResult(TEN_INT, Integer.parseInt(TEN_STRING));
    }

    @Test
    public void defineFirstResultShouldSaveNineAsNewLastResultWhenTotalCountIsTenAndFirstResultIsZeroAndMaxResultIsTen() {
        paginator.setTotalNumberOfEntities(TEN_LONG);
        when(request.getParameter("maxResult")).thenReturn(TEN_STRING);
        PowerMockito.when(FormulasManager.defineFormula(request)).thenReturn(formula);
        when(formula.calculateFirstResult(anyInt(), anyInt())).thenReturn(0);
        paginator.defineFirstResult();
        verify(session).setAttribute(ENTITY_NAME, 9);
    }
     @Test
    public void defineFirstResultShouldSaveTenAsNewLastResultWhenTotalCountIsTenAndFirstResultIsOneAndMaxResultIsTen() {
        paginator.setTotalNumberOfEntities(TEN_LONG);
        when(request.getParameter("maxResult")).thenReturn(TEN_STRING);
        PowerMockito.when(FormulasManager.defineFormula(request)).thenReturn(formula);
        when(formula.calculateFirstResult(anyInt(), anyInt())).thenReturn(2);
        paginator.defineFirstResult();
        verify(session).setAttribute(ENTITY_NAME, 10);
    }

}
