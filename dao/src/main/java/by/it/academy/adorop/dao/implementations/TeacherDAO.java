package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.model.users.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAO extends BasicUserDAO<Teacher> {

    @Autowired
    public TeacherDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Teacher> getPersistedClass() {
        return Teacher.class;
    }
}
