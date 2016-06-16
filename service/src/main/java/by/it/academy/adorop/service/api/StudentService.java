package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.exceptions.ServiceException;

public interface StudentService extends UserService<Student> {
    void registerForTheCourse(Student student, Course course) throws ServiceException;
}
