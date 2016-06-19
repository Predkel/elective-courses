package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.implementations.MarkDAOImpl;
import by.it.academy.adorop.dao.implementations.TeacherDAO;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;

public class TeacherServiceImpl extends BasicUserService<Teacher> implements TeacherService {

    private final MarkDAO markDAO;

    TeacherServiceImpl(MarkDAO markDAO, UserDAO<Teacher> userDAO) {
        this.markDAO = markDAO;
        this.userDAO = userDAO;
    }

    private TeacherServiceImpl() {
        markDAO = MarkDAOImpl.getInstance();
        userDAO = TeacherDAO.getInstance();
    }

    public static TeacherServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void evaluate(Mark mark) throws ServiceException {
        try {
            transaction = HibernateUtils.beginTransaction();
            markDAO.update(mark);
            transaction.commit();
        } catch (DaoException e) {
            catchDaoException(e);
        }
    }

    @Override
    public void addCourse(Teacher teacher, Course course) throws ServiceException {
        try {
            teacher.addCourse(course);
            transaction = HibernateUtils.beginTransaction();
            userDAO.update(teacher);
            transaction.commit();
        } catch (DaoException e) {
            catchDaoException(e);
        }
    }

    @Override
    protected DAO<Teacher, Long> getDAO() {
        return userDAO;
    }

    private static class InstanceHolder {
        static final TeacherServiceImpl INSTANCE = new TeacherServiceImpl();
    }
}
