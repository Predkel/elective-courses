package by.it.academy.adorop.web.utils;

public class RequestParamValidator {

    private RequestParamValidator() {
    }

    public static boolean areEmpty(String... params) {
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
}
