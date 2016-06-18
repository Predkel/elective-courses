package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EvaluateCommand extends BasicCommandVerifyingRequest {
    
    private static final Logger LOGGER = Logger.getLogger(EvaluateCommand.class);

    private final IdValidator<Course> courseIdValidator;
    private final IdValidator<Mark> markIdValidator;
    private final TeacherService teacherService;

    public EvaluateCommand(HttpServletRequest request, IdValidator<Course> courseIdValidator,
                           IdValidator<Mark> markIdValidator, TeacherService teacherService) {
        super(request);
        this.courseIdValidator = courseIdValidator;
        this.markIdValidator = markIdValidator;
        this.teacherService = teacherService;
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
