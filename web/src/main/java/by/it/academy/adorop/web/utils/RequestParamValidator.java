package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.exceptions.ServiceException;

public class RequestParamValidator {

    private RequestParamValidator() {
    }

    public static boolean hasEmpty(String... params) {
        for (String param : params) {
            if (param == null || param.equals("")) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isEmpty(String param) {
        return param == null || param.equals("");
    }

    public static boolean isPositiveInt(String param) {
        return !isEmpty(param) && param.matches("[0-9]+");
    }

    public static boolean isNumberBetweenZeroAndTen(String param) {
        return !isEmpty(param) && param.matches("[0-9]|(10)");
    }

    @SuppressWarnings("unchecked")
    public static boolean isValidId(String idParameter, Service service) throws ServiceException {
        return isPositiveInt(idParameter) && service.find(Long.valueOf(idParameter)) != null;
    }
}
