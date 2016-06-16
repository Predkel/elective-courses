package by.it.academy.adorop.web.utils;

import javax.servlet.http.HttpServletRequest;

public class PathBuilder {

    private PathBuilder() {
    }

    public static String buildPath(HttpServletRequest request, String operation) {
        return request.getServletPath() + "?operation=" + operation;
    }

    public static String buildPath(HttpServletRequest request, String operation, String nameOfParameter, String parameter) {
        return request.getServletPath() + "?operation=" + operation + "&" + nameOfParameter + "=" + parameter;
    }
}
