package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.web.config.handlers.annotations.ModelById;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final CourseService courseService;
    private final TeacherService teacherService;
    private final MarkService markService;

    @Autowired
    public TeacherController(CourseService courseService, TeacherService teacherService, MarkService markService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.markService = markService;
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

    @RequestMapping("/course")
    @PreAuthorize("#course.teacher.equals(principal)")
    public String showCourse(@ModelById(nameOfIdParameter = "courseId") Course course, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("marks", markService.getByCourse(course));
        return "teachers/course";
    }

    //TODO markValue validation
    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    public String evaluate(@ModelById(nameOfIdParameter = "markId") Mark mark,
                           @RequestParam Long courseId,
                           @RequestParam Integer markValue) {
        mark.setValue(markValue);
        teacherService.evaluate(mark);
        return "redirect:/teachers/course?courseId=" + courseId;
    }
}
