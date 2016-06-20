package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.ERROR_PAGE;

class ErrorCommand implements Command{

    private final HttpServletRequest request;
    private static final Logger logger = Logger.getLogger(ErrorCommand.class);

    ErrorCommand(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void execute(HttpServletResponse response) {
        try {
            Dispatcher.forward(ERROR_PAGE, request, response);
        } catch (ServletException | IOException e) {
            logger.fatal(e);
        }
    }
}
