package integration.by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.web.security.authentication.UserAuthentication;
import org.springframework.security.core.Authentication;

public class AuthenticationUtilsForTests {
    public static Authentication authenticatedStudent() {
        Student student = buildStudent();
        UserAuthentication<Student> authentication = UserAuthentication.newInstance(student);
        authentication.setAuthenticated(true);
        return authentication;
    }

    private static Student buildStudent() {
        Student student = new Student();
        student.setId(10002L);
        student.setDocumentId("adorop88");
        student.setFirstName("1stName");
        student.setLastName("lastName");
        return student;
    }
    public static Authentication authenticatedTeacher() {
        Teacher authenticatedTeacher = buildTeacher();
        UserAuthentication<Teacher> teacherUserAuthentication = UserAuthentication.newInstance(authenticatedTeacher);
        teacherUserAuthentication.setAuthenticated(true);
        return teacherUserAuthentication;
    }

    private static Teacher buildTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setDocumentId("adorop");
        teacher.setFirstName("first Name");
        teacher.setLastName("Last Name");
        return teacher;
    }
}
