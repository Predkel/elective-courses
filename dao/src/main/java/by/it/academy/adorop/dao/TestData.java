package by.it.academy.adorop.dao;

import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.implementations.CourseDaoImpl;
import by.it.academy.adorop.dao.implementations.StudentDAO;
import by.it.academy.adorop.dao.implementations.TeacherDAO;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.model.users.Teacher;
import org.hibernate.Transaction;

class TestData {
    public static void main(String[] args) throws DaoException {

        try {
            DAO<Teacher, Long> dao = TeacherDAO.getInstance();
            Transaction transaction = HibernateUtils.beginTransaction();
            Teacher teacher = new Teacher();
            teacher.setDocumentId("adorop");
            teacher.setPassword("1234");
            teacher.setFirstName("first Name");
            teacher.setLastName("Last Name");
            dao.persist(teacher);


            DAO<Course, Long> courseDAO = CourseDaoImpl.getInstance();
            for (int i = 0; i < 10000; i++) {
                Course course = new Course();
                course.setTitle("title_" + i);
                course.setDescription("description_" + i);
                course.setTeacher(teacher);
                courseDAO.persist(course);
            }

            DAO<Student, Long> studentDAO = StudentDAO.getInstance();
            Student student = new Student();
            student.setDocumentId("adorop88");
            student.setPassword("1234");
            student.setFirstName("1stName");
            student.setLastName("lastName");
            studentDAO.persist(student);

            transaction.commit();
        } finally {
            HibernateUtils.closeSessionFactory();
        }
    }
}
