package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Student;

public class AuthenticationCommandForStudentsTest extends AuthenticationCommandTest<Student> {

    @Override
    String defineServletPath() {
        return "/students";
    }

    @Override
    String defineNameOfUserAttributeForSession() {
        return "student";
    }

    @Override
    String definePathToMainCommand() {
        return "/students?operation=main";
    }

    @Override
    Student getConcreteUserInstance() {
        return new Student();
    }
}
