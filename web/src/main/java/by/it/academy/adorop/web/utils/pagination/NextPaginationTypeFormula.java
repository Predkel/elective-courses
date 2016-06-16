package by.it.academy.adorop.web.utils.pagination;


public class NextPaginationTypeFormula implements Formula {
    @Override
    public int calculateFirstResult(int lastResult, int maxResult) {
        return lastResult + 1;
    }
}
