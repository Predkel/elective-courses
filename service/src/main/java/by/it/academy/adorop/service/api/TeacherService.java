package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;

public interface TeacherService extends UserService<Teacher> {
    void addCourse(Teacher teacher, Course course);
}
