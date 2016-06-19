package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand extends BasicCommandVerifyingRequest {

    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);

    protected RegisterCommand(HttpServletRequest request) {
        super(request);
    }


    @Override
    protected boolean requestIsValid() {
        return false;
    }

    @Override
    protected void prepareResponse() {

    }

    @Override
    protected void move(HttpServletResponse response) {

    }

    @Override
    protected void setExplainingMessage() {

    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) {

    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
