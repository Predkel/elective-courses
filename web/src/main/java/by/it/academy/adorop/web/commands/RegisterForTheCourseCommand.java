package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;
import by.it.academy.adorop.web.utils.PathBuilder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterForTheCourseCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterForTheCourseCommand.class);

    private final IdValidator<Course> idValidator;
    private final StudentService studentService;

    public RegisterForTheCourseCommand(IdValidator<Course> idValidator, StudentService studentService) {
        this.idValidator = idValidator;
        this.studentService = studentService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String courseIdParam = request.getParameter("courseId");
            if (!idValidator.isValid(courseIdParam)) {
                Dispatcher.forwardToMainWithFollowTheLinkMessage(request, response);
            } else {
                registerStudentForTheCourse(request);
                redirectToShowCourse(request, response, courseIdParam);
            }
        } catch (ServiceException | IOException | ServletException e) {
            LOGGER.error(e);
            CommandsFactory.createErrorCommand().execute(request, response);
        }
    }

    private void redirectToShowCourse(HttpServletRequest request, HttpServletResponse response, String courseIdParam) throws IOException {
        String pathToShowCourse = PathBuilder.buildPath(request, OPERATION_SHOW_COURSE, "courseId", courseIdParam);
        response.sendRedirect(pathToShowCourse);
    }

    private void registerStudentForTheCourse(HttpServletRequest request) throws ServiceException {
        Course course = idValidator.getValidModel();
        Student student = (Student) request.getSession().getAttribute("student");
        studentService.registerForTheCourse(student, course);
    }
}
