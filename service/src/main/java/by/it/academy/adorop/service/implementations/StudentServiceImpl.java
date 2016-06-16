package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.implementations.StudentDAO;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.exceptions.ServiceException;

public class StudentServiceImpl extends BasicUserService<Student> implements StudentService {

    private StudentServiceImpl() {
        userDAO = StudentDAO.getInstance();
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
    protected DAO<Student, Long> getDAO() {
        return userDAO;
    }

    private static class InstanceHolder {
        static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
    }
}
