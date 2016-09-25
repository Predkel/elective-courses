package by.it.academy.adorop.service.implementations.with.database.visit;

import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.service.implementations.PersistenceTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, PersistenceTestConfig.class})
public abstract class AbstractIntegrationTest {
}
