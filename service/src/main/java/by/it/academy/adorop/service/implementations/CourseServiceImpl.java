package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.CourseDAO;
import by.it.academy.adorop.dao.utils.CatchAndRethrow;
import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@CatchAndRethrow(exceptionToCatch = RuntimeException.class, rethrow = ServiceException.class)
public class CourseServiceImpl extends BasicService<Course, Long> implements CourseService {

    private CourseDAO courseDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    protected CourseDAO getDAO() {
        return courseDAO;
    }

    @Override
    public boolean isAlreadyExists(Course course) {
        Map<String, Object> uniquePropertiesToValues = new HashMap<>();
        uniquePropertiesToValues.put("title", course.getTitle());
        uniquePropertiesToValues.put("teacher.id", course.getTeacher().getId());
        return courseDAO.findSingleResultBy(uniquePropertiesToValues) != null;
    }
}
