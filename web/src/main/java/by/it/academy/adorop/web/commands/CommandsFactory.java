package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.service.implementations.CourseServiceImpl;
import by.it.academy.adorop.service.implementations.MarkServiceImpl;
import by.it.academy.adorop.service.implementations.StudentServiceImpl;
import by.it.academy.adorop.service.implementations.TeacherServiceImpl;
import by.it.academy.adorop.web.utils.IdValidator;
import by.it.academy.adorop.web.utils.pagination.PaginatorImpl;

import javax.servlet.http.HttpServletRequest;

import static by.it.academy.adorop.web.utils.RequestParamValidator.isEmpty;

public class CommandsFactory {

    private CommandsFactory(){}

    public static Command createCommand(HttpServletRequest request) {

        Command command;
        String operation = request.getParameter("operation");
        if (isEmpty(operation)) {
            command = new AuthenticationCommand<>(request, injectUserService(request));
        } else if (operation.equals("main")) {
            command = createMainCommand(request);
        } else if (operation.equals("showCourse")) {
            command = createShowCourseCommand(request);
        } else if (operation.equals("registerForTheCourse") && !requestIsFromTeacher(request)) {
            command = new RegisterForTheCourseCommand(request);
        } else if (operation.equals("evaluate")) {
            command = new EvaluateCommand(request);
        } else if (operation.equals("addCourse") && requestIsFromTeacher(request)) {
            command = new AddCourseCommand(request);
        } else if (operation.equals("saveCourse") && requestIsFromTeacher(request)) {
            command = new SaveCourseCommand(request);
        } else if (operation.equals("register")) {
            command = new RegisterCommand(request);
        } else if (operation.equals("saveUser")) {
            command = new SaveUserCommand<>(request, injectUserService(request));
        } else {
            command = createMainCommand(request);
        }
        return command;
    }

    public static Command createAuthenticationCommand(HttpServletRequest request) {
        return new AuthenticationCommand<>(request, injectUserService(request));
    }

    public static Command createErrorCommand(HttpServletRequest request) {
        return new ErrorCommand(request);
    }

    private static boolean requestIsFromTeacher(HttpServletRequest request) {
        return request.getServletPath().equals("/teachers");
    }

    private static Command createShowCourseCommand(HttpServletRequest request) {
        Command command;
        String servletPath = request.getServletPath();
        if (servletPath.equals("/students")) {
            command = new ShowCourseForStudentCommand(request);
        } else {
            command = new ShowCourseForTeacherCommand(request);
        }
        return command;
    }

    private static Command createMainCommand(HttpServletRequest request) {
        return new MainCommand(request, CourseServiceImpl.getInstance(), new PaginatorImpl("course", request));
    }

    private static IdValidator<Course> createCourseIdValidator() {
        return new IdValidator<>(CourseServiceImpl.getInstance());
    }

    @SuppressWarnings("unchecked")
    private static<T extends User> UserService<T> injectUserService(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        UserService service;
        if (servletPath.equals("/students")) {
            service = StudentServiceImpl.getInstance();
        } else {
            service = TeacherServiceImpl.getInstance();
        }
        return service;
    }
}
