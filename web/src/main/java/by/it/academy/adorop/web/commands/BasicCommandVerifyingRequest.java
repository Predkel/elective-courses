package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BasicCommandVerifyingRequest extends BasicCommand {

    String ADD_COURSE_FORM = "views/addCourseForm.jsp";
    String REGISTER_FORM = "views/register.jsp";
    String SHOULD_BE_A_NUMBER_MESSAGE = "Should be a number from Zero to Ten";
    String THE_SAME_COURSE_ALREADY_EXISTS_MESSAGE = "The same course already exists";
    String USER_ALREADY_EXISTS_MESSAGE = "User with the same document id already exists";

    HttpServletRequest request;

    protected BasicCommandVerifyingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public void execute(HttpServletResponse response) {
        try {
            if (requestIsValid()) {
                super.execute(response);
            } else {
                setExplainingMessage();
                //TODO имя
                sendToRelevantPage(response);
            }
        } catch (ServiceException | ServletException | IOException e) {
            catchExceptions(e, response);
        }
    }

    protected abstract boolean requestIsValid() throws ServiceException;

    protected abstract void prepareResponse() throws ServiceException;

    protected abstract void move(HttpServletResponse response) throws IOException, ServletException;

    protected abstract void setExplainingMessage() throws ServiceException;

    protected abstract void sendToRelevantPage(HttpServletResponse response) throws ServletException, IOException;

    protected abstract Logger getLogger();
}