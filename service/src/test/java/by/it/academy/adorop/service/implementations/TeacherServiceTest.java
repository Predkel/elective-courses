package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HibernateUtils.class})
public class TeacherServiceTest {

    private static final String DOCUMENT_ID = "documentId";
    private static final String PASSWORD = "password";

    private TeacherService teacherService;
    @Mock
    UserDAO<Teacher> userDAO;
    @Mock
    MarkDAO markDAO;
    @Mock
    Teacher teacher;
    @Mock
    Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HibernateUtils.class);
        teacherService = new TeacherServiceImpl(markDAO, userDAO);
        PowerMockito.when(HibernateUtils.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void isValidShouldReturnFalseWhenUserWithGivenDocumentIdDoesNotExist() throws DaoException, ServiceException {
        when(userDAO.getByDocumentId(DOCUMENT_ID)).thenReturn(null);
        assertFalse(teacherService.isValid(DOCUMENT_ID, PASSWORD));
        verify(userDAO).getByDocumentId(DOCUMENT_ID);
    }

    @Test
    public void isValidShouldReturnFalseWhenGivenPasswordDoesNotMatchWithPasswordOfRetrievedUser() throws DaoException, ServiceException {
        Teacher teacher = buildTeacher("1234");
        when(userDAO.getByDocumentId(DOCUMENT_ID)).thenReturn(teacher);
        assertFalse(teacherService.isValid(DOCUMENT_ID, PASSWORD));
    }
    @Test
    public void isValidShouldReturnTrueWhenGivenPasswordMatchesWithPasswordOfRetrievedUser() throws DaoException, ServiceException {
        Teacher teacher = buildTeacher(PASSWORD);
        when(userDAO.getByDocumentId(DOCUMENT_ID)).thenReturn(teacher);
        assertTrue(teacherService.isValid(DOCUMENT_ID, PASSWORD));
    }

    @Test(expected = ServiceException.class)
    public void isValidShouldThrowServiceExceptionWhenDaoExceptionIsThrown() throws DaoException, ServiceException {
        when(userDAO.getByDocumentId(anyString())).thenThrow(new DaoException());
        teacherService.isValid(DOCUMENT_ID, PASSWORD);
    }

    @Test
    public void getByDocumentIdShouldReturnTheSameUserWhichDaoReturns() throws DaoException, ServiceException {
        Teacher teacherFromDao = buildTeacher(PASSWORD);
        when(userDAO.getByDocumentId(anyString())).thenReturn(teacherFromDao);
        Teacher teacherFromService = teacherService.getByDocumentId(DOCUMENT_ID);
        assertEquals(teacherFromDao, teacherFromService);
    }

    @Test(expected = ServiceException.class)
    public void getByDocumentIdShouldThrowServiceExceptionWhenDaoExceptionIsThrown() throws DaoException, ServiceException {
        when(userDAO.getByDocumentId(anyString())).thenThrow(new DaoException());
        teacherService.getByDocumentId(DOCUMENT_ID);
    }

    @Test
    public void addCourseShouldReturnFalseWhenTeacherAlreadyHasSameCourse() throws Exception {
        when(teacher.addCourse(anyObject())).thenReturn(false);
        assertFalse(teacherService.addCourse(teacher, new Course()));
    }

    @Test
    public void addCourseShouldReturnTrueWhenCourseIsNew() throws Exception {
        whenCourseIsNew();
        assertTrue(teacherService.addCourse(teacher, new Course()));
    }

    private void whenCourseIsNew() {
        when(teacher.addCourse(anyObject())).thenReturn(true);
    }

    @Test
    public void addCourseShouldCommitChanges() throws Exception {
        whenCourseIsNew();
        teacherService.addCourse(teacher, new Course());
        verify(userDAO).update(teacher);
        verify(transaction).commit();
    }

    private Teacher buildTeacher(String password) {
        Teacher teacher = new Teacher();
        teacher.setDocumentId(DOCUMENT_ID);
        teacher.setPassword(password);
        return teacher;
    }

}
