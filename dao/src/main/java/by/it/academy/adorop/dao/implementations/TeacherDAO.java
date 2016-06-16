package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.model.users.Teacher;

public class TeacherDAO extends BasicUserDAO<Teacher> {

    private TeacherDAO() {
    }

    public static TeacherDAO getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Class<Teacher> getPersistedClass() {
        return Teacher.class;
    }

    private static class InstanceHolder {
        private static final TeacherDAO INSTANCE = new TeacherDAO();
    }
}
