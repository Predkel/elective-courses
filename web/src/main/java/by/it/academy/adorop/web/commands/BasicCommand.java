package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BasicCommand implements Command {

    final HttpServletRequest request;

    protected BasicCommand(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void execute(HttpServletResponse response) {
        try {
            prepareResponse();
            move(response);
        } catch (ServiceException | IOException | ServletException e) {
            catchExceptions(e, response);
        }
    }

    void catchExceptions(Exception e, HttpServletResponse response) {
        getLogger().error(e);
        CommandsFactory.createErrorCommand(request).execute(response);
    }

    protected abstract Logger getLogger();

    protected abstract void prepareResponse() throws ServiceException;

    protected abstract void move(HttpServletResponse response) throws IOException, ServletException;
}
