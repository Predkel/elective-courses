package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.model.users.Student;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO extends BasicUserDAO<Student> {

    @Autowired
    public StudentDAO(Session session) {
        super(session);
    }

    @Override
    protected Class<Student> getPersistedClass() {
        return Student.class;
    }
}
