package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCourseCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddCourseCommand.class);

    AddCourseCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            setPathToProcessForm(request);
            Dispatcher.forward(ADD_COURSE_FORM, request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private void setPathToProcessForm(HttpServletRequest request) {
        request.setAttribute("processFormPath", request.getServletPath());
    }
}
