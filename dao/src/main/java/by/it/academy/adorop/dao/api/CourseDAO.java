package by.it.academy.adorop.dao.api;

import by.it.academy.adorop.model.Course;

import java.util.List;

public interface CourseDAO extends DAO<Course, Long> {
    List<Course> getBunch(int firstResult, int maxResult);
}
