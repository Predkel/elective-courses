package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.model.Course;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "by.it.academy.adorop.model.Course")
public class CourseDaoImpl extends BasicDAO<Course, Long> implements CourseDAO {

    @Autowired
    public CourseDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Course> persistedClass() {
        return Course.class;
    }
}
