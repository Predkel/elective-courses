package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.web.utils.RequestParamValidator;

import javax.servlet.http.HttpServletRequest;

public class PaginatorBuilder {

    public static final int DEFAULT_MAX_RESULT = 10;
    public static final int DEFAULT_FIRST_RESULT = 0;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    private final HttpServletRequest request;
    private final Paginator paginator;

    private final String maxResultParameter;
    private final String pageNumberParameter;
    private final String firstResultParameter;

    public PaginatorBuilder(HttpServletRequest request) {
        this(request, new Paginator(request));
    }

    PaginatorBuilder(HttpServletRequest request, Paginator paginator) {
        this.request = request;
        this.paginator = paginator;
        maxResultParameter = request.getParameter("maxResult");
        pageNumberParameter = request.getParameter("pageNumber");
        firstResultParameter = request.getParameter("firstResult");
    }

    public Paginator buildPaginator() {
        processMaxResult();
        processFirstResult();
        processPageNumber();
        return paginator;
    }

    private void processMaxResult() {
        int maxResult = defineMaxResult();
        paginator.setMaxResult(maxResult);
        request.setAttribute("maxResult", maxResult);
    }

    private void processFirstResult() {
        int firstResult = defineFirstResult();
        paginator.setFirstResult(firstResult);
        request.setAttribute("firstResult", firstResult);
    }

    private void processPageNumber() {
        int currentPage = defineCurrentPage();
        paginator.setCurrentPage(currentPage);
        request.setAttribute("currentPage", currentPage);
    }

    private int defineCurrentPage() {
        int currentPage;
        if (goalOfRequestIsToMoveToPage()) {
            currentPage = Integer.parseInt(pageNumberParameter);
        } else if (goalOfRequestIsToChangeMaxResult()) {
            currentPage = (int) Math.ceil((double) defineFirstResult() / (double) defineMaxResult());
        } else {
            currentPage = DEFAULT_PAGE_NUMBER;
        }
        return currentPage;
    }

    private int defineFirstResult() {
        int firstResult;
        if (goalOfRequestIsToChangeMaxResult()) {
            firstResult = Integer.parseInt(firstResultParameter);
        } else if (goalOfRequestIsToMoveToPage()) {
            firstResult = defineMaxResult() * (Integer.parseInt(pageNumberParameter) - 1);
        } else {
            firstResult = DEFAULT_FIRST_RESULT;
        }
        return firstResult;
    }

    private boolean goalOfRequestIsToChangeMaxResult() {
        return RequestParamValidator.isPositiveInt(firstResultParameter) &&
                RequestParamValidator.isEmpty(pageNumberParameter);
    }

    private boolean goalOfRequestIsToMoveToPage() {
        return RequestParamValidator.isPositiveInt(pageNumberParameter) &&
                RequestParamValidator.isEmpty(firstResultParameter);
    }

    private int defineMaxResult() {
        return RequestParamValidator.isPositiveInt(maxResultParameter) ? Integer.parseInt(maxResultParameter)
                : DEFAULT_MAX_RESULT;
    }
}
