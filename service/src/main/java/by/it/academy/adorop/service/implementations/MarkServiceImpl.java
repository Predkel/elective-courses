package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MarkServiceImpl extends BasicService<Mark, Long> implements MarkService {

    private final MarkDAO markDAO;

    @Autowired
    public MarkServiceImpl(MarkDAO markDAO) {
        this.markDAO = markDAO;
    }

    @Override
    public Mark getByStudentAndCourse(Student student, Course course) {
        return markDAO.getByStudentAndCourse(student, course);
    }

    @Override
    public List<Mark> getByCourse(Course course) {
        return markDAO.getByCourse(course);
    }

    @Override
    protected DAO<Mark, Long> getDAO() {
        return markDAO;
    }

    @Override
    public boolean isAlreadyExists(Mark mark) {
        Map<String, Object> uniquePropertiesToValues = new HashMap<>();
        uniquePropertiesToValues.put("student.id", mark.getStudent().getId());
        uniquePropertiesToValues.put("course.id", mark.getCourse().getId());
        return !markDAO.getBy(uniquePropertiesToValues).isEmpty();
    }
}
