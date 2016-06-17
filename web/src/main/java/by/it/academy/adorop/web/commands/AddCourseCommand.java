package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCourseCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AddCourseCommand.class);

    public AddCourseCommand(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected boolean requestIsValid() {
        return false;
    }

    @Override
    protected void setContent() {

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
