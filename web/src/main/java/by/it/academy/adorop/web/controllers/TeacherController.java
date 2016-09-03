package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends AbstractUserController<Teacher> {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    protected UserService<Teacher> service() {
        return teacherService;
    }
}
