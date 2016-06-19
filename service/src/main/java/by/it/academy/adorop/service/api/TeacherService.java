package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.exceptions.ServiceException;

public interface TeacherService extends UserService<Teacher> {
    void evaluate(Mark mark) throws ServiceException;
    void addCourse(Teacher teacher, Course course) throws ServiceException;
}
