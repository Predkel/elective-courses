package by.it.academy.adorop.web.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String OPERATION_MAIN = "main";
    String MAIN_PAGE = "views/main.jsp";
    String OPERATION_SHOW_COURSE = "showCourse";
    String COURSE_FOR_STUDENTS_PAGE = "views/courseForStudent.jsp";
    String COURSE_FOR_TEACHER_PAGE = "views/courseForTeacher.jsp";
    String ADD_COURSE_FORM = "views/addCourseForm.jsp";
    String REGISTER_FORM = "views/register.jsp";
    String OPERATION_REGISTER_FOR_THE_COURSE = "registerForTheCourse";
    String FOLLOW_THE_LINK_MESSAGE = "To watch a course follow the link";
    String SHOULD_BE_A_NUMBER_MESSAGE = "Should be a number from Zero to Ten";
    String SHOULD_BE_NOT_EMPTY_MESSAGE = "Field should be not empty";
    String THE_SAME_COURSE_ALREADY_EXISTS_MESSAGE = "The same course already exists";
    String USER_ALREADY_EXISTS_MESSAGE = "User with the same document id already exists";

    void execute(HttpServletRequest request, HttpServletResponse response);
}
