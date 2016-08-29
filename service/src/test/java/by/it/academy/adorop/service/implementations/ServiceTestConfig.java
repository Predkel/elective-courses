package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.dao.api.DAO;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
public class ServiceTestConfig {
    @Bean
    @Profile("withMocks")
    public CourseDAO courseDaoImpl() {
        return Mockito.mock(CourseDAO.class);
    }
}
