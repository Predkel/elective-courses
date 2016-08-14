package by.it.academy.adorop.web.utils.pagination;

import by.it.academy.adorop.service.api.CourseService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class PaginationAspect {

    private CourseService courseService;
    @Autowired
    public PaginationAspect(CourseService courseService) {
        this.courseService = courseService;
    }

    @Before("@annotation(pagination) && args(request)")
    public void putPaginationContent(Pagination pagination, HttpServletRequest request){
        PaginationContentPutter.putPaginationContent(request, courseService, pagination.bunchAttributeName());
    }
}
