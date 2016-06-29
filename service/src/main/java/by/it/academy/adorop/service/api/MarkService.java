package by.it.academy.adorop.service.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;

import java.util.List;

public interface MarkService extends Service<Mark, Long> {
    Mark getByStudentAndCourse(Student student, Course course);
    List<Mark> getByCourse(Course course);
}
