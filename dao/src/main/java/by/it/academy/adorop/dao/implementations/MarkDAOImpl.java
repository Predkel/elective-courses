package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarkDAOImpl extends BasicDAO<Mark, Long> implements MarkDAO {

    @Autowired
    public MarkDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Mark> getByCourse(Course course) {
        return currentSession().createCriteria(Mark.class)
                .add(Restrictions.eq("course", course))
                .list();
    }

    @Override
    public Mark getByStudentAndCourse(Student student, Course course) {
        return (Mark) currentSession().createCriteria(Mark.class)
                .add(Restrictions.eq("student", student))
                .add(Restrictions.eq("course", course))
                .uniqueResult();
    }

    @Override
    protected Class<Mark> getPersistedClass() {
        return Mark.class;
    }
}
