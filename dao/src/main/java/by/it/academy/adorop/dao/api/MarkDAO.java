package by.it.academy.adorop.dao.api;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;

import java.util.List;

public interface MarkDAO extends DAO<Mark, Long> {
    List<Mark> getByCourse(Course course);
    Mark getByStudentAndCourse(Student student, Course course);
}
