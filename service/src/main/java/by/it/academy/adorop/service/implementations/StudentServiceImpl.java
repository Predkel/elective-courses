package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.implementations.MarkDAOImpl;
import by.it.academy.adorop.dao.implementations.StudentDAO;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.hibernate.Transaction;

public class StudentServiceImpl extends BasicUserService<Student> implements StudentService {

    private final MarkDAO markDAO;

    private StudentServiceImpl() {
        userDAO = StudentDAO.getInstance();
        markDAO = MarkDAOImpl.getInstance();
    }

    public static StudentServiceImpl getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void registerForTheCourse(Student student, Course course) throws ServiceException {
        student.addMark(new Mark(student, course));
        try {
            transaction = HibernateUtils.beginTransaction();
            userDAO.update(student);
            transaction.commit();
        } catch (DaoException e) {
            catchDaoException(e);
        }
    }

    @Override
    public boolean isCourseListener(Student student, Course course) throws ServiceException {
        boolean isCourseListener = false;
        try {
            Transaction transaction = HibernateUtils.beginTransaction();
            Mark mark = markDAO.getByStudentAndCourse(student, course);
            transaction.commit();
            isCourseListener = mark != null;
        } catch (DaoException e) {
            catchDaoException(e);
        }
        return isCourseListener;
    }

    @Override
    protected DAO<Student, Long> getDAO() {
        return userDAO;
    }

    private static class InstanceHolder {
        static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
    }
}
