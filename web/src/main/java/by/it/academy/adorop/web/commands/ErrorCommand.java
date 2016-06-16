package by.it.academy.adorop.web.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ErrorCommand implements Command {

    private static final String ERROR_PAGE = "views/error.jsp";
    private static final Logger logger = Logger.getLogger(ErrorCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(ERROR_PAGE);
        } catch (IOException e) {
            logger.fatal(e);
        }
    }
}
