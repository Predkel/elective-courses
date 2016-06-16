package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.web.utils.RequestParamValidator;

import javax.servlet.http.HttpServletRequest;

public class PaginatorImpl implements Paginator {

    static final int DEFAULT_FIRST_RESULT = 0;
    static final int DEFAULT_MAX_RESULT = 10;

    private final String entityName;
    private final HttpServletRequest request;
    private Long totalCount;

    public PaginatorImpl(String entityName, HttpServletRequest request) {
        this.entityName = entityName;
        this.request = request;
    }

    @Override
    public int defineMaxResult() {
        int maxResult = readMaxResult();
        saveMaxResult(maxResult, request);
        return maxResult;
    }

    private int readMaxResult() {
        String maxResultParam = request.getParameter("maxResult");
        int maxResult;
        if (!RequestParamValidator.isPositiveInt(maxResultParam)) {
            maxResult = DEFAULT_MAX_RESULT;
        } else {
            maxResult = Integer.parseInt(maxResultParam);
        }
        return maxResult;
    }

    private void saveMaxResult(int maxResult, HttpServletRequest request){
        request.setAttribute("maxResult", maxResult);
    }

    @Override
    public int defineFirstResult() {
        int lastResult = readPreviouslyWatchedLastResult(request);
        Formula formula = FormulasManager.defineFormula(request);
        int firstResult = formula.calculateFirstResult(lastResult, readMaxResult());
        saveNewLastResult(firstResult, request);
        return firstResult;
    }

    @Override
    public void setTotalNumberOfEntities(Long numberOfEntities) {
        totalCount = numberOfEntities;
    }

    private int readPreviouslyWatchedLastResult(HttpServletRequest request) {
        Object lastResultAttribute = request.getSession().getAttribute(entityName);
        int lastResult;
        if (lastResultAttribute == null) {
            lastResult = calculateLastResult(DEFAULT_FIRST_RESULT);
        } else {
            lastResult = (Integer) lastResultAttribute;
        }
        return lastResult;
    }

    private void saveNewLastResult(int firstResult, HttpServletRequest request) {
        request.getSession().setAttribute(entityName, calculateLastResult(firstResult));
    }

    private int calculateLastResult(int firstResult) {
        int lastResult = firstResult + readMaxResult() - 1;
        return lastResult < totalCount ? lastResult : totalCount.intValue();
    }

}