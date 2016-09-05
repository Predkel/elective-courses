package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.PostHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController extends AbstractUserController<Student> {
    private final StudentService studentService;
    private final PostHandler<Student> postHandler;

    @Autowired
    public StudentController(StudentService studentService,
                             @Qualifier("studentPostHandler") PostHandler<Student> postHandler) {
        this.studentService = studentService;
        this.postHandler = postHandler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createNew(@RequestBody @Valid Student student, Errors errors) {
        return postHandler.createNew(student, errors);
    }
}
