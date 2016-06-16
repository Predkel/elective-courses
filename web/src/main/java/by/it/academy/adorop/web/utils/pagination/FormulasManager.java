package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.web.utils.RequestParamValidator;

import javax.servlet.http.HttpServletRequest;

class FormulasManager {

    private FormulasManager() {}

    static Formula defineFormula(HttpServletRequest request) {
        String paginationType = request.getParameter("paginationType");
        Formula formula;
        if (RequestParamValidator.isEmpty(paginationType)) {
            formula = new DefaultPaginationTypeFormula();
        } else if (paginationType.equals("previous")) {
            formula = new PreviousPaginationTypeFormula();
        } else if (paginationType.equals("next")){
            formula = new NextPaginationTypeFormula();
        } else {
            formula = new DefaultPaginationTypeFormula();
        }
        return formula;
    }
}
