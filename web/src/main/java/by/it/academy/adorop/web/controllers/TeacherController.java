package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Teacher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    public String showCourses() {
        return "main/teachers";
    }
}
