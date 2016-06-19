package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.service.utils.ServiceUtils;
import by.it.academy.adorop.web.commands.Command;
import by.it.academy.adorop.web.commands.CommandsFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Command command = CommandsFactory.createCommand(request);
            command.execute(response);
        } catch (Exception e) {
            e.printStackTrace();
            Command errorCommand = CommandsFactory.createErrorCommand(request);
            errorCommand.execute(response);
        }
    }
    @Override
    public void destroy() {
        ServiceUtils.releaseResources();
    }
}
