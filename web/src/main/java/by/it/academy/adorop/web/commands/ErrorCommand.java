package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ErrorCommand extends BasicCommand {

    private static final String ERROR_PAGE = "views/error.jsp";
    private static final Logger logger = Logger.getLogger(ErrorCommand.class);

    protected ErrorCommand(HttpServletRequest request) {
        super(request);
    }


    @Override
    protected void prepareResponse() throws ServiceException {

    }

    @Override
    protected void move(HttpServletResponse response) throws IOException{

    }

    @Override
    protected Logger getLogger() {
        return null;
    }
}
