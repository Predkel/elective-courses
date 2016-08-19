package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.config.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, PersistenceTestConfig.class})
public class CourseServiceImplTest {

    @Autowired
    private CourseService courseService;

    @Test
    public void testGetCount() throws Exception {
        assertSame(3L, courseService.getTotalCount());
    }
}