package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.implementations.MarkDAOImpl;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.hibernate.Transaction;

import java.util.List;

public class MarkServiceImpl extends BasicService<Mark, Long> implements MarkService {

    private final MarkDAO markDAO;

    private MarkServiceImpl() {
        markDAO = MarkDAOImpl.getInstance();
    }

    public static MarkServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Mark getByStudentAndCourse(Student student, Course course) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            Mark mark = markDAO.getByStudentAndCourse(student, course);
            transaction.commit();
            return mark;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Mark> getByCourse(Course course) throws ServiceException {
        try {
            Transaction transaction = HibernateUtils.beginTransaction();
            List<Mark> marks = markDAO.getByCourse(course);
            transaction.commit();
            return marks;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    protected DAO<Mark, Long> getDAO() {
        return markDAO;
    }

    private static class InstanceHolder {
        static final MarkServiceImpl INSTANCE = new MarkServiceImpl();
    }
}
