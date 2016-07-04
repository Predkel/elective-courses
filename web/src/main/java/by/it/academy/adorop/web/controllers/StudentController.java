package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import by.it.academy.adorop.web.utils.pagination.PaginatorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final MarkService markService;

    @Autowired
    public StudentController(CourseService courseService, StudentService studentService, MarkService markService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.markService = markService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String main(Model model, HttpServletRequest request) {
        Paginator paginator = getPaginator(request);
        putCourses(model, paginator);
        putPagesNumbers(model, paginator);
        return "main/students";
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

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/course/{courseId}")
    public String showCourse(Model model, @AuthenticationPrincipal Student student, @PathVariable("courseId") Long courseId) {
        setContent(model, student, courseId);
        return "course/student";
    }

    private void setContent(Model model, @AuthenticationPrincipal Student student, @PathVariable("courseId") Long courseId) {
        Course course = courseService.find(courseId);
        model.addAttribute("course", course);
        boolean isCourseListener = studentService.isCourseListener(student, course);
        model.addAttribute("isCourseListener", isCourseListener);
        if (isCourseListener) {
            model.addAttribute("mark", markService.getByStudentAndCourse(student, course));
        }
    }
}
