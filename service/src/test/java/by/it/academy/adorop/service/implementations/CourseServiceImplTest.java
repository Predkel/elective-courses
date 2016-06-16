package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.implementations.CourseDaoImpl;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HibernateUtils.class})
public class CourseServiceImplTest {

    private static final int ANY_INT = 1;
    private final Course course = new Course();

    private CourseService courseService;
    @Mock
    private CourseDaoImpl courseDAO;
    @Mock
    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HibernateUtils.class);
        courseService = new CourseServiceImpl(courseDAO);
        PowerMockito.when(HibernateUtils.beginTransaction()).thenReturn(transaction);
    }

    @Test(expected = ServiceException.class)
    public void getBunchShouldThrowServiceExceptionWhenDaoExceptionWasThrown() throws Exception {
        when(courseDAO.getBunch(anyInt(), anyInt())).thenThrow(new DaoException());
        courseService.getBunch(ANY_INT, ANY_INT);
    }

    @Test
    public void saveShouldCommitChanges() throws Exception {
        courseService.save(course);
        verify(transaction).commit();
    }

    @Test
    public void testCatchingDaoException() throws DaoException {
        DaoException daoException = new DaoException();
        when(courseDAO.persist(anyObject())).thenThrow(daoException);
        try {
            courseService.save(new Course());
        } catch (ServiceException e) {
            assertEquals(daoException, e.getCause());
        }
    }
}
