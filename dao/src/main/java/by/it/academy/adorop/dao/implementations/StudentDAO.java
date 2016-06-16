package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.model.users.Student;

public class StudentDAO extends BasicUserDAO<Student> {

    private StudentDAO() {}

    public static StudentDAO getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Class<Student> getPersistedClass() {
        return Student.class;
    }

    private static class InstanceHolder {
        private static final StudentDAO INSTANCE = new StudentDAO();
    }
}
