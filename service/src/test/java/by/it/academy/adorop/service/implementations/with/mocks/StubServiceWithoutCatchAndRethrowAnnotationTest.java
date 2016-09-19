package by.it.academy.adorop.service.implementations.with.mocks;

import by.it.academy.adorop.dao.api.CourseDAO;
import org.hibernate.HibernateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;

public class StubServiceWithoutCatchAndRethrowAnnotationTest extends IntegrationTestWithMocks {
    @Autowired
    CourseDAO courseDAO;
    @Autowired
    StubServiceWithoutCatchAndRethrowAnnotation stubServiceWithoutCatchAndRethrowAnnotation;

    @Test(expected = HibernateException.class)
    public void shouldThrowTheSameExceptionThatInDaoWathThrown() throws Exception {
        when(courseDAO.getAll()).thenThrow( new HibernateException(""));
        stubServiceWithoutCatchAndRethrowAnnotation.test();
    }
}