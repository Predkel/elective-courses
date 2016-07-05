package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @RequestMapping
    public String showCourses(HttpServletRequest request) {
        PaginationContentPutter.putPaginationContent(request, courseService, "courses");
        return "main/teachers";
    }

    @RequestMapping("/add")
    public String addCourse(Model model, @ModelAttribute Course course) {
        model.addAttribute("course", course);
        return "course/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewCourse(@Valid Course course,
                                BindingResult bindingResult,
                                Model model,
                                @AuthenticationPrincipal Teacher teacher) {
        if (bindingResult.hasErrors()) {
            return addCourse(model, course);
        }
        teacherService.addCourse(teacher, course);
        return "redirect:/teachers";
    }
}
