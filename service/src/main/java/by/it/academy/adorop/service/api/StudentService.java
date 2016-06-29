package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;

public interface StudentService extends UserService<Student> {
    void registerForTheCourse(Student student, Course course);
    boolean isCourseListener(Student student, Course course);
}
