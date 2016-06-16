package by.it.academy.adorop.dao.implementations;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.dao.exceptions.DaoException;
import by.it.academy.adorop.dao.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HibernateUtils.class})
public class CourseDaoImplTest {

    private CourseDAO courseDAO;
    @Mock
    private Session session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HibernateUtils.class);
        courseDAO = new CourseDaoImpl();
        PowerMockito.when(HibernateUtils.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testGetBunch() {
        HibernateException hibernateException = new HibernateException(new Exception());
        when(session.createQuery(anyString())).thenThrow(hibernateException);
        try {
            courseDAO.getBunch(1, 2);
        } catch (DaoException e) {
            assertEquals(hibernateException, e.getCause());
        }

    }
}