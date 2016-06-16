package by.it.academy.adorop.web.commands;

import by.it.academy.adorop.model.users.Teacher;

public class AuthenticationCommandForTeachersTest extends AuthenticationCommandTest<Teacher> {

    @Override
    String defineServletPath() {
        return "/teachers";
    }

    @Override
    String defineNameOfUserAttributeForSession() {
        return "teacher";
    }

    @Override
    String definePathToMainCommand() {
        return "/teachers?operation=main";
    }

    @Override
    Teacher getConcreteUserInstance() {
        return new Teacher();
    }

}
