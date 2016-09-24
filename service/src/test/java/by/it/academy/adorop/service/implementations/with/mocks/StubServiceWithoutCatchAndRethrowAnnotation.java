package by.it.academy.adorop.service.implementations.with.mocks;

import by.it.academy.adorop.dao.api.CourseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StubServiceWithoutCatchAndRethrowAnnotation {
    private final CourseDAO courseDAO;

    @Autowired
    public StubServiceWithoutCatchAndRethrowAnnotation(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void test() {
        courseDAO.findAll();
    }
}
