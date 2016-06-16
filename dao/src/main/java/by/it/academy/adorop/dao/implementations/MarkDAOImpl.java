package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static by.it.academy.adorop.dao.utils.HibernateUtils.getCurrentSession;

public class MarkDAOImpl extends BasicDAO<Mark, Long> implements MarkDAO {

    private MarkDAOImpl() {
    }

    public static MarkDAOImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Mark> getByCourse(Course course) throws DaoException {
        try {
            Session session = getCurrentSession();
            return session.createCriteria(Mark.class)
                    .add(Restrictions.eq("course", course))
                    .list();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Mark getByStudentAndCourse(Student student, Course course) throws DaoException {
        try {
            Session session = getCurrentSession();
            return (Mark) session.createCriteria(Mark.class)
                    .add(Restrictions.eq("student", student))
                    .add(Restrictions.eq("course", course))
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Class<Mark> getPersistedClass() {
        return Mark.class;
    }

    private static class InstanceHolder {
        private static final MarkDAOImpl INSTANCE = new MarkDAOImpl();
    }
}
