package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.web.utils.Dispatcher;
import by.it.academy.adorop.web.utils.IdValidator;
import by.it.academy.adorop.web.utils.PathBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ShowCourseForStudentCommand extends BasicShowCourseCommand {

    ShowCourseForStudentCommand(MarkService markService, IdValidator<Course> idValidator) {
        super(markService, idValidator);
    }

    @Override
    void setContent(HttpServletRequest request, Course course) throws ServiceException {
        request.setAttribute("course", course);
        Student student = getStudent(request);
        setIsCourseListener(request, student, course);
        setPathToRegisterOnTheCourse(request);
    }

    @Override
    void forward(HttpServletRequest request, HttpServletResponse response, Course course) throws ServletException, IOException {
        Dispatcher.forward(COURSE_FOR_STUDENTS_PAGE, request, response);
    }

    private void setPathToRegisterOnTheCourse(HttpServletRequest request) {
        request.setAttribute("action", PathBuilder.buildPath(request, OPERATION_REGISTER_FOR_THE_COURSE));
    }

    private void setIsCourseListener(HttpServletRequest request, Student student, Course course) throws ServiceException {
        Mark mark = markService.getByStudentAndCourse(student, course);
        boolean isCourseListener = false;
        if (mark != null) {
            isCourseListener = true;
            request.setAttribute("mark", mark);
        }
        request.setAttribute("isCourseListener", isCourseListener);
    }

    private Student getStudent(HttpServletRequest request) {
        return  (Student) request.getSession().getAttribute("student");
    }
}
