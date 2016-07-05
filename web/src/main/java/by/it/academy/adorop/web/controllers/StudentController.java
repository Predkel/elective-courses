package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import by.it.academy.adorop.web.utils.pagination.Paginator;
import by.it.academy.adorop.web.utils.pagination.PaginatorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with the same document id already exists";
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
    public String showCourses(HttpServletRequest request) {
        PaginationContentPutter.putPaginationContent(request, courseService, "courses");
        return "main/students";
    }

    @RequestMapping("/course/{courseId}")
    public String showCourse(Model model, @AuthenticationPrincipal Student student, @PathVariable Long courseId) {
        setContent(model, student, courseId);
        return "course/student";
    }

    private void setContent(Model model, Student student, Long courseId) {
        Course course = courseService.find(courseId);
        model.addAttribute("course", course);
        boolean isCourseListener = studentService.isCourseListener(student, course);
        model.addAttribute("isCourseListener", isCourseListener);
        if (isCourseListener) {
            model.addAttribute("mark", markService.getByStudentAndCourse(student, course));
        }
    }

    //TODO: idValidation
    @RequestMapping("/registerForTheCourse/{courseId}")
    public String registerForTheCourse(@PathVariable Long courseId, @AuthenticationPrincipal Student student) {
        registerStudent(courseId, student);
        return redirectToShowCourse(courseId);
    }

    private void registerStudent(Long courseId, Student student) {
        studentService.registerForTheCourse(student, courseService.find(courseId));
    }

    private String redirectToShowCourse(@PathVariable Long courseId) {
        return "redirect:/students/course/" + courseId;
    }

    @RequestMapping("/new")
    public String register(Model model) {
        model.addAttribute("user", new Student());
        return "register";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveNewStudent(@Valid Student student, BindingResult bindingResult, Model model) {
        String path = definePath(student, bindingResult);
        processRequest(student, bindingResult, model);
        return path;
    }
//TODO name
    private void processRequest(Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", student);
        } else if (studentService.isAlreadyExists(student.getDocumentId())) {
            model.addAttribute("message", USER_ALREADY_EXISTS_MESSAGE);
            model.addAttribute("user", student);
        } else {
            studentService.persist(student);
        }
    }

    private String definePath(Student student, BindingResult bindingResult) {
        String path;
        if (bindingResult.hasErrors() || studentService.isAlreadyExists(student.getDocumentId())) {
            path = "register";
        } else {
            path = "redirect:/students";
        }
        return path;
    }
}
