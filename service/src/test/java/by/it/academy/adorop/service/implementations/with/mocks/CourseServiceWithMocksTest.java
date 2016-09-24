package by.it.academy.adorop.service.implementations.with.mocks;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.PersistenceTestConfig;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import static org.mockito.Mockito.when;


public class CourseServiceWithMocksTest extends IntegrationTestWithMocks {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseDAO courseDAO;

    @Test(expected = ServiceException.class)
    public void shouldThrowServiceExceptionWhenRuntimeExceptionWasThrown() throws Exception {
        when(courseDAO.getCount()).thenThrow(new RuntimeException());
        courseService.getCount();
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowServiceExceptionWhenSubClassOfRuntimeExceptionWasThrown() {
        when(courseDAO.getCount()).thenThrow(new HibernateException("test"));
        courseService.getCount();
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowServiceExceptionWhenSubClassOfHibernateExceptionWasThrown() throws Exception {
        when(courseDAO.getCount()).thenThrow(new ConstraintViolationException("test", new SQLException(), "test"));
        courseService.getCount();
    }
}
