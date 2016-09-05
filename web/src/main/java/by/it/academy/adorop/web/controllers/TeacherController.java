package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
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
@RequestMapping("/teachers")
public class TeacherController extends AbstractUserController<Teacher> {
    private final TeacherService teacherService;
    private final PostHandler<Teacher> postHandler;

    @Autowired
    public TeacherController(TeacherService teacherService,
                             @Qualifier("teacherPostHandler") PostHandler<Teacher> postHandler) {
        this.teacherService = teacherService;
        this.postHandler = postHandler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createNew(@RequestBody @Valid Teacher teacher, Errors errors) {
        return postHandler.createNew(teacher, errors);
    }
}
