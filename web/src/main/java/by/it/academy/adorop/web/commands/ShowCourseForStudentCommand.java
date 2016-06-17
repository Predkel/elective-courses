package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;
import by.it.academy.adorop.web.utils.PathBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ShowCourseForStudentCommand extends BasicShowCourseCommand {


    public ShowCourseForStudentCommand(HttpServletRequest request, IdValidator<Course> idValidator, MarkService markService) {
        super(request, idValidator, markService);
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
