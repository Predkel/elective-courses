package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.exceptions.ServiceException;

import java.util.List;

public interface MarkService extends Service<Mark, Long> {
    Mark getByStudentAndCourse(Student student, Course course) throws ServiceException;
    List<Mark> getByCourse(Course course) throws ServiceException;
}
