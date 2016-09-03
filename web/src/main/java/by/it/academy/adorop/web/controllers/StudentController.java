package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.controllers.AbstractUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentController extends AbstractUserController<Student> {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected UserService<Student> service() {
        return studentService;
    }
}
