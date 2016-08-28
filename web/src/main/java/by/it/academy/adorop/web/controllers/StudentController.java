package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Student;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @RequestMapping(value = "/currentPrincipal", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Student getCurrentPrincipal(@AuthenticationPrincipal Student student) {
        student.setPassword(null);
        return student;
    }
}
