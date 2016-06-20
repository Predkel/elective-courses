package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.*;

public class RegisterCommand extends BasicCommand {

    RegisterCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected void prepareResponse() {
        request.setAttribute("processFormPath", request.getServletPath());
    }

    @Override
    protected void move(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(REGISTRATION_PAGE, request, response);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(RegisterCommand.class);
    }
}
