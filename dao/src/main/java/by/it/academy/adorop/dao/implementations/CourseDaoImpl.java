package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.model.Course;
import org.hibernate.HibernateException;

import java.util.List;

import static by.it.academy.adorop.dao.utils.HibernateUtils.getCurrentSession;

public class CourseDaoImpl extends BasicDAO<Course, Long> implements CourseDAO {

    CourseDaoImpl() {
    }

    public static CourseDaoImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Course> getBunch(int firstResult, int maxResult) throws DaoException {
        try {
            return getCurrentSession()
                    .createQuery("from Course")
                    .setCacheable(true)
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .list();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Class<Course> getPersistedClass() {
        return Course.class;
    }


    private static class InstanceHolder {
        private static final CourseDaoImpl INSTANCE = new CourseDaoImpl();
    }
}
