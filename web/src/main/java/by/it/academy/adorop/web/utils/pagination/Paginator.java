package by.it.academy.adorop.web.utils.pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Paginator {

    private static final int DEFAULT_RANGE_OF_LEFT_SIDE = 3;
    private static final int DEFAULT_RANGE_OF_RIGHT_SIDE = 3;
    private final HttpServletRequest request;
    private int firstResult;
    private int maxResult;
    private int currentPage;

    public Paginator(HttpServletRequest request) {
        this.request = request;
    }

    public List<Integer> getPagesNumbers(Long totalCountOfEntities) {
        List<Integer> numbersOfPages = new ArrayList<>();
        addFirstPage(numbersOfPages);
        int rangeOfRightSide = fillRangeOfLeftSide(numbersOfPages);
        int numberOfLastPage = defineNumberOfLastPage(totalCountOfEntities);
        rangeOfRightSide = addCurrentPage(numbersOfPages, rangeOfRightSide, numberOfLastPage);
        fillRangeOfRightSide(numbersOfPages, rangeOfRightSide, numberOfLastPage);
        addLastPage(numbersOfPages, numberOfLastPage);
        return numbersOfPages;
    }

    private void addFirstPage(List<Integer> numbersOfPages) {
        numbersOfPages.add(1);
    }

    private void addLastPage(List<Integer> numbersOfPages, int numberOfLastPage) {
        numbersOfPages.add(numberOfLastPage);
    }

    private void fillRangeOfRightSide(List<Integer> numbersOfPages, int rangeOfRightSide, int numberOfLastPage) {
        for (int pageNumber = currentPage; rangeOfRightSide > 0; rangeOfRightSide--) {
            pageNumber++;
            if (pageNumber < numberOfLastPage) {
                numbersOfPages.add(pageNumber);
            }
        }
    }

    private int addCurrentPage(List<Integer> numbersOfPages, int rangeOfRightSide, int numberOfLastPage) {
        if (currentPage != 1 && currentPage != numberOfLastPage) {
            numbersOfPages.add(currentPage);
        } else {
            rangeOfRightSide++;
        }
        return rangeOfRightSide;
    }

    private int fillRangeOfLeftSide(List<Integer> numbersOfPages) {
        int rangeOfLeftSide = DEFAULT_RANGE_OF_LEFT_SIDE;
        int rangeOfRightSide = DEFAULT_RANGE_OF_RIGHT_SIDE;

        for (int pageNumber = currentPage - rangeOfLeftSide - 1; rangeOfLeftSide > 0; rangeOfLeftSide--) {
            pageNumber++;
            if (pageNumber < 2) {
                rangeOfRightSide++;
            } else {
                numbersOfPages.add(pageNumber);
            }
        }
        return rangeOfRightSide;
    }

    private int defineNumberOfLastPage(Long totalCountOfEntities) {
        return (int) Math.ceil((double) totalCountOfEntities / (double) maxResult);
    }

    public int getFirstResult() {
        return firstResult;
    }

    void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
