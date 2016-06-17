package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ErrorCommand extends Command {

    private static final String ERROR_PAGE = "views/error.jsp";
    private static final Logger logger = Logger.getLogger(ErrorCommand.class);

    protected ErrorCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected boolean requestIsValid() throws ServiceException, IOException, ServletException {
        return false;
    }

    @Override
    protected void setContent() throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void goFurther(HttpServletResponse response) throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void setExplainingMessage() throws ServiceException, IOException, ServletException {

    }

    @Override
    protected void sendToRelevantPage(HttpServletResponse response) throws ServiceException, IOException, ServletException {

    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
