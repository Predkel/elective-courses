package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveCourseCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SaveCourseCommand.class);

    private final TeacherService teacherService;

    public SaveCourseCommand(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        try {
            if (RequestParamValidator.isEmpty(title)) {
                Dispatcher.forwardWithMessage(ADD_COURSE_FORM, request, response, SHOULD_BE_NOT_EMPTY_MESSAGE);
            } else {
                Course course = buildCourse(title, description);
                boolean courseNotExists = saveCourseIfNotExists(course, getCurrentTeacher(request));
                goFurther(courseNotExists, request, response);
            }
        } catch (ServletException | IOException | ServiceException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private void goFurther(boolean isSuccessfulAdding, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isSuccessfulAdding) {
            goToMain(request, response);
        } else {
            Dispatcher.forwardWithMessage(ADD_COURSE_FORM, request, response, THE_SAME_COURSE_ALREADY_EXISTS_MESSAGE);
        }
    }

    private void goToMain(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathToMain = PathBuilder.buildPath(request, OPERATION_MAIN);
        response.sendRedirect(pathToMain);
    }

    private boolean saveCourseIfNotExists(Course course, Teacher currentTeacher) throws ServiceException {
        return teacherService.addCourse(currentTeacher, course);
    }

    private Teacher getCurrentTeacher(HttpServletRequest request) {
        return (Teacher) request.getSession().getAttribute("teacher");
    }

    private Course buildCourse(String title, String description) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        return course;
    }
}
