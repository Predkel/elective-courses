package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.model.users.Teacher;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAO extends BasicUserDAO<Teacher> {

    @Autowired
    public TeacherDAO(Session session) {
        super(session);
    }

    @Override
    protected Class<Teacher> getPersistedClass() {
        return Teacher.class;
    }
}
