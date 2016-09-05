package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.post.PostHandler;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.put.PutHandler;
import by.it.academy.adorop.web.infrastructure.resolvers.annotations.ModelById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/marks")
public class MarkController {
    private final MarkService markService;
    private final Validator validator;
    private final PostHandler<Mark> postHandler;
    private final PutHandler<Mark> putHandler;

    @Autowired
    public MarkController(MarkService markService,
                          @Qualifier("markValidator") Validator validator,
                          @Qualifier("markPostHandler") PostHandler<Mark> postHandler,
                          @Qualifier("markPutHandler") PutHandler<Mark> putHandler) {
        this.markService = markService;
        this.validator = validator;
        this.postHandler = postHandler;
        this.putHandler = putHandler;
    }

    @InitBinder
    public void setValidator(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("#mark != null and #mark.student != null and #mark.student.equals(principal)")
    public ResponseEntity createNew(@RequestBody @Valid Mark mark, Errors errors) {
        return postHandler.createNew(mark, errors);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            params = {"courseId"})
    @PreAuthorize("#course.teacher.equals(principal)")
    @ResponseBody
    public List<Mark> getBy(@ModelById(nameOfIdParameter = "courseId", serviceClass = CourseService.class) Course course) {
        return markService.getByCourse(course);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            params = {"courseId", "studentId"})
    @ResponseBody
    @PreAuthorize("principal.equals(#student)")
    public Mark getBy(@ModelById(nameOfIdParameter = "courseId", serviceClass = CourseService.class) Course course,
                      @ModelById(nameOfIdParameter = "studentId", serviceClass = StudentService.class) Student student) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("student", student);
        properties.put("course", course);
        return markService.getSingleResultBy(properties);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasPermission(#mark, 'update')")
    public ResponseEntity<Void> update(@RequestBody @Valid Mark mark, Errors errors) {
        return putHandler.update(mark, errors);
    }
}
