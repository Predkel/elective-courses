package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl extends BasicUserService<Student> implements StudentService {

    @Autowired
    private MarkDAO markDAO;

    @Override
    public void registerForTheCourse(Student student, Course course) {
        markDAO.persist(new Mark(student, course));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public boolean isCourseListener(Student student, Course course) {
        Mark mark = markDAO.getByStudentAndCourse(student, course);
        return mark != null;
    }

    @Override
    protected DAO<Student, Long> getDAO() {
        return userDAO;
    }
}
