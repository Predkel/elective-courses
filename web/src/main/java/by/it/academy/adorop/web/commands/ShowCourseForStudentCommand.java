package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.StudentServiceImpl;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.PathBuilder;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.it.academy.adorop.web.utils.Constants.*;

class ShowCourseForStudentCommand extends BasicShowCourseCommand {

    private final StudentService studentService;

    ShowCourseForStudentCommand(HttpServletRequest request) {
        super(request);
        studentService = StudentServiceImpl.getInstance();
    }

    ShowCourseForStudentCommand(HttpServletRequest request, MarkService markService, CourseService courseService, StudentService studentService) {
        super(request, markService, courseService);
        this.studentService = studentService;
    }

    @Override
    protected boolean requestIsValid() throws ServiceException {
        return RequestParamValidator.isValidId(courseIdParameter, courseService);
    }

    @Override
    protected void prepareResponse() throws ServiceException {
        Course course = getCourseById();
        setCourse(course);
        Student student = getCurrentStudent();
        boolean isCourseListener = studentService.isCourseListener(student, course);
        setIsCourseListener(isCourseListener);
        setMark(course, student, isCourseListener);
        setPathToProcessRegistrationForTheCourse();
    }

    private Student getCurrentStudent() {
        return (Student) request.getSession().getAttribute("student");
    }

    private Course getCourseById() throws ServiceException {
        return courseService.find(Long.valueOf(courseIdParameter));
    }

    private void setMark(Course course, Student student, boolean isCourseListener) throws ServiceException {
        if (isCourseListener) {
          request.setAttribute("mark", markService.getByStudentAndCourse(student, course));
        }
    }

    private void setIsCourseListener(boolean isCourseListener) {
        request.setAttribute("isCourseListener", isCourseListener);
    }

    private void setCourse(Course course) {
        request.setAttribute("course", course);
    }

    private void setPathToProcessRegistrationForTheCourse() {
        String path = PathBuilder.buildPath(request, OPERATION_REGISTER_FOR_THE_COURSE, "courseId", courseIdParameter);
        request.setAttribute("pathToProcessRegistrationForTheCourse", path);
    }

    @Override
    protected void move(HttpServletResponse response) throws ServletException, IOException {
        Dispatcher.forward(COURSE_FOR_STUDENTS_PAGE, request, response);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(ShowCourseForStudentCommand.class);
    }
}
