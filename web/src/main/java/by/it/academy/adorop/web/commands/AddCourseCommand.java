package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.web.utils.Constants;
import by.it.academy.adorop.web.utils.Dispatcher;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.*;

public class AddCourseCommand extends BasicCommand {

    private static final String TEACHERS_CONTROLLER_PATH = "/teachers";

    AddCourseCommand(HttpServletRequest request) {
        super(request);
    }


    @Override
    protected void prepareResponse() {
        request.setAttribute("processFormPath", TEACHERS_CONTROLLER_PATH);
    }

    @Override
    protected void move(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(ADD_COURSE_FORM, request, response);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(AddCourseCommand.class);
    }
}
