package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherServiceImpl extends BasicUserService<Teacher> implements TeacherService {

    private final MarkDAO markDAO;
    @Autowired
    public TeacherServiceImpl(UserDAO<Teacher> userDAO, MarkDAO markDAO) {
        super(userDAO);
        this.markDAO = markDAO;
    }


    @Override
    public void evaluate(Mark mark) {
        if (mark.getId() == null) {
            throw new IllegalArgumentException("mark id should be not null");
        }
        if (mark.getCourse() == null || mark.getStudent() == null) {
            mark = markDAO.get(mark.getId());
        }
        markDAO.update(mark);
    }

    @Override
    public void addCourse(Teacher teacher, Course course) {
        teacher.addCourse(course);
        userDAO.update(teacher);
    }

    @Override
    protected DAO<Teacher, Long> getDAO() {
        return userDAO;
    }
}
