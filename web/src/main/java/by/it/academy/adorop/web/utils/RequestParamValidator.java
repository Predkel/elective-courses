package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.service.api.Service;

public class RequestParamValidator {

    private RequestParamValidator() {
    }

    public static boolean isEmpty(String param) {
        return param == null || param.equals("");
    }

    public static boolean isPositiveNumber(String param) {
        return !isEmpty(param) && param.matches("[0-9]+");
    }

    public static boolean isNumberBetweenZeroAndTen(String param) {
        return !isEmpty(param) && param.matches("[0-9]|(10)");
    }
}
