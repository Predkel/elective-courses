package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.config.handlers.annotations.ModelById;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/students")
public class StudentController extends AbstractUserController<Student> {

    public static final String PATH_TO_CONTROLLER = "/students";
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService, MarkService markService) {
        super(courseService, markService);
        this.studentService = studentService;
    }

    @RequestMapping("/course")
    public String showCourse(Model model,
                             @AuthenticationPrincipal Student student,
                             @ModelById(nameOfIdParameter = "courseId") Course course) {
        setContent(model, student, course);
        return "students/course";
    }

    private void setContent(Model model, Student student, Course course) {
        model.addAttribute("course", course);
        boolean isCourseListener = studentService.isCourseListener(student, course);
        model.addAttribute("isCourseListener", isCourseListener);
        if (isCourseListener) {
            model.addAttribute("mark", markService.getByStudentAndCourse(student, course));
        }
    }

    @RequestMapping("/registerForTheCourse")
    public String registerForTheCourse(@ModelById(nameOfIdParameter = "courseId") Course course,
                                       @AuthenticationPrincipal Student student) {
        register(course, student);
        return redirectToShowCourse(course);
    }

    private void register(Course course, Student student) {
        studentService.registerForTheCourse(student, course);
    }

    private String redirectToShowCourse(Course course) {
        return "redirect:/students/course?courseId=" + course.getId();
    }

    @RequestMapping("/new")
    public String register(Model model) {
        model.addAttribute("user", new Student());
        return "registration";
    }

    @Override
    protected String getPathToController() {
        return PATH_TO_CONTROLLER;
    }

    @Override
    protected UserService<Student> getUserService() {
        return studentService;
    }
}
