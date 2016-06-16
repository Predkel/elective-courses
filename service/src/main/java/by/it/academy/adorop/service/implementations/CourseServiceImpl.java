package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.dao.implementations.CourseDaoImpl;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;

public class CourseServiceImpl extends BasicService<Course, Long> implements CourseService {

    private final CourseDAO courseDAO;

    private CourseServiceImpl() {
        courseDAO = CourseDaoImpl.getInstance();
    }

    CourseServiceImpl(CourseDaoImpl courseDAO) {
        this.courseDAO = courseDAO;
    }

    public static CourseServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected CourseDAO getDAO() {
        return courseDAO;
    }

    private static class InstanceHolder {
        static final CourseServiceImpl INSTANCE = new CourseServiceImpl();
    }

}
