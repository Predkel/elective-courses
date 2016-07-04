package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import by.it.academy.adorop.web.utils.pagination.PaginatorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final CourseService courseService;

    @Autowired
    public StudentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main(Model model, HttpServletRequest request) {
        setContent(model, request);
        return "main/students";
    }

    private void setContent(Model model, HttpServletRequest request) {
        Paginator paginator = getPaginator(request);
        putCourses(model, paginator);
        putPagesNumbers(model, paginator);
    }

    private void putPagesNumbers(Model model, Paginator paginator) {
        Long totalCount = courseService.getTotalCount();
        List<Integer> pagesNumbers = paginator.getPagesNumbers(totalCount);
        model.addAttribute("numbersOfPages", pagesNumbers);
    }

    private void putCourses(Model model, Paginator paginator) {
        int firstResult = paginator.getFirstResult();
        int maxResult = paginator.getMaxResult();
        model.addAttribute("courses", courseService.getBunch(firstResult, maxResult));
    }

    private Paginator getPaginator(HttpServletRequest request) {
        PaginatorBuilder paginatorBuilder = PaginatorBuilder.newInstance(request);
        return paginatorBuilder.buildPaginator();
    }

    @RequestMapping("/test")
    public String testCurrentSession(Model model, @AuthenticationPrincipal Student student) {
        List<Course> courses = courseService.getBunch(0, 10);
        courses.addAll(courseService.getBunch(10, 10));

        model.addAttribute("courses", courses);

        model.addAttribute("course2", courseService.find(2L));
        model.addAttribute("course3", courseService.find(3L));
        System.out.println(student);
        return "test";
    }
}
