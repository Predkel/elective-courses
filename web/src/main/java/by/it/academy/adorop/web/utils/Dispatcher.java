package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.web.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher {

    private Dispatcher(){}

    public static void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }

    public static void forwardWithMessage(String path, HttpServletRequest request, HttpServletResponse response,
                              String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        forward(path, request, response);

    }

    public static void forwardToMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathToMain = PathBuilder.buildPath(request, Constants.OPERATION_MAIN);
        forward(pathToMain, request, response);
    }
}
