package by.it.academy.adorop.web.utils.pagination;

class DefaultPaginationTypeFormula implements Formula {
    @Override
    public int calculateFirstResult(int lastResult, int maxResult) {
        return PaginatorImpl.DEFAULT_FIRST_RESULT;
    }
}
