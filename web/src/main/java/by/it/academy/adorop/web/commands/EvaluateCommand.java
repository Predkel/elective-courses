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

public class EvaluateCommand implements Command {
    
    private static final Logger LOGGER = Logger.getLogger(EvaluateCommand.class);

    private final IdValidator<Course> courseIdValidator;
    private final IdValidator<Mark> markIdValidator;
    private final TeacherService teacherService;

    EvaluateCommand(IdValidator<Course> idValidator, IdValidator<Mark> markIdValidator, TeacherService teacherService) {
        this.courseIdValidator = idValidator;
        this.markIdValidator = markIdValidator;
        this.teacherService = teacherService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String courseIdParam = request.getParameter("courseId");
        String markIdParam = request.getParameter("markId");
        try {
            if (!areIdsValid(request, courseIdParam, markIdParam)) {
                Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
            } else {
                String markValueParameter = request.getParameter("markValue");
                Mark validMark = markIdValidator.getValidModel();
                evaluate(markValueParameter, validMark);

                String pathToShowCourse = PathBuilder.buildPath(request, OPERATION_SHOW_COURSE, "courseId", courseIdParam);
                goToShowCourse(pathToShowCourse, request, response, markValueParameter);
            }
        } catch (ServiceException | IOException | ServletException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private void goToShowCourse(String pathToShowCourse, HttpServletRequest request,
                                HttpServletResponse response, String markValueParameter) throws IOException, ServletException {
        if (isValidMarkValue(markValueParameter)) {
            response.sendRedirect(pathToShowCourse);
        } else {
            Dispatcher.forwardWithMessage(pathToShowCourse, request, response, SHOULD_BE_A_NUMBER_MESSAGE);
        }
    }

    private void evaluate(String markValueParameter, Mark mark) throws ServiceException {
        if (isValidMarkValue(markValueParameter)) {
            mark.setValue(Integer.valueOf(markValueParameter));
            teacherService.evaluate(mark);
        }
    }

    private boolean isValidMarkValue(String markValueParameter) {
        return RequestParamValidator.isNumberBetweenZeroAndTen(markValueParameter);
    }

    private boolean areIdsValid(HttpServletRequest request, String courseIdParam, String markIdParam) throws ServiceException {
        return courseIdValidator.isValid(courseIdParam)
                && markIdValidator.isValid(markIdParam)
                && CourseSecurity.isTeacherOfTheCourse(getTeacher(request), courseIdValidator.getValidModel());
    }

    private Teacher getTeacher(HttpServletRequest request) {
        return (Teacher) request.getSession().getAttribute("teacher");
    }
}
