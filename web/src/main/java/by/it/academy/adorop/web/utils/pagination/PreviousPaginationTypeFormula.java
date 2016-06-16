package by.it.academy.adorop.web.utils.pagination;

public class PreviousPaginationTypeFormula implements Formula {
    @Override
    public int calculateFirstResult(int lastResult, int maxResult) {
        int firstResult = lastResult - maxResult * 2 + 1;
        if (firstResult < 0) {
            firstResult = 0;
        }
        return firstResult;
    }
}
