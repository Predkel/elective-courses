package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class MainController {

    @Autowired
    CourseService courseService;

    @RequestMapping(method = RequestMethod.GET)
    public String test(Model model) {
        List<Course> courses = courseService.getBunch(0, 10);
        model.addAttribute("courses", courses);
        return "test";
    }
}
