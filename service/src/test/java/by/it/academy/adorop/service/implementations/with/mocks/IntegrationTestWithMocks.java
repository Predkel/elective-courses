package by.it.academy.adorop.service.implementations.with.mocks;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.service.implementations.PersistenceTestConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, PersistenceTestConfig.class, ServiceTestConfigWithMocks.class})
@ActiveProfiles("withMocks")
public abstract class IntegrationTestWithMocks {
    @Autowired
    CourseDAO courseDAO;
    @Before
    public void setUp() throws Exception {
        Mockito.reset(courseDAO);
    }
}
