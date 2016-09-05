package by.it.academy.adorop.web.infrastructure.http.method.handlers.post;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("coursePostHandler")
public class CoursePostHandler extends AbstractPostHandler<Course, Long> {
    private final CourseService courseService;

    @Autowired
    public CoursePostHandler(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected Service<Course, Long> service() {
        return courseService;
    }
}
