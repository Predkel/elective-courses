package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final CourseService courseService;

    @Autowired
    public TeacherController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping
    public String showCourses(HttpServletRequest request) {
        PaginationContentPutter.putPaginationContent(request, courseService, "courses");
        return "main/teachers";
    }
}
