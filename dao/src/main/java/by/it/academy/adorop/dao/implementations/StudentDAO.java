package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.model.users.Student;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO extends BasicUserDAO<Student> {

    @Autowired
    public StudentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Student> getPersistedClass() {
        return Student.class;
    }
}
