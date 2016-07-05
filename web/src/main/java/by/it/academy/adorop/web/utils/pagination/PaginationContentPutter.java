package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.service.api.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

public class PaginationContentPutter {
    public static<T, ID extends Serializable> void putPaginationContent(HttpServletRequest request,
                                                                        Service<T, ID> service,
                                                                        String nameOfAttribute) {
        Paginator paginator = getPaginator(request);
        putEntities(request, paginator, service, nameOfAttribute);
        putPagesNumbers(request, paginator, service);
    }

    private static Paginator getPaginator(HttpServletRequest request) {
        PaginatorBuilder paginatorBuilder = PaginatorBuilder.newInstance(request);
        return paginatorBuilder.buildPaginator();
    }

    private static<T, ID extends Serializable> void putEntities(HttpServletRequest request,
                                                                Paginator paginator,
                                                                Service<T, ID> service,
                                                                String nameOfAttribute) {
        int firstResult = paginator.getFirstResult();
        int maxResult = paginator.getMaxResult();
        request.setAttribute(nameOfAttribute, service.getBunch(firstResult, maxResult));
    }

    private static<T, ID extends Serializable> void putPagesNumbers(HttpServletRequest request,
                                                                    Paginator paginator,
                                                                    Service<T, ID> service) {
        Long totalCount = service.getTotalCount();
        List<Integer> pagesNumbers = paginator.getPagesNumbers(totalCount);
        request.setAttribute("numbersOfPages", pagesNumbers);
    }
}
