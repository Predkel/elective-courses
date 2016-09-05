package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.post.PostHandler;
import by.it.academy.adorop.web.infrastructure.validators.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final PostHandler<Course> postHandler;

    @Autowired
    public CourseController(CourseService courseService, @Qualifier("coursePostHandler") PostHandler<Course> postHandler) {
        this.courseService = courseService;
        this.postHandler = postHandler;
    }

    @InitBinder
    public void setValidator(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new CourseValidator());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Course>> getBunch(@RequestParam int firstResult, @RequestParam int maxResults) {
        if (firstResult < 0 || maxResults < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(courseService.getBunch(firstResult, maxResults), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("#course != null and #course.teacher != null and #course.teacher.equals(principal)")
    public ResponseEntity createNew(@RequestBody @Valid Course course, Errors errors) {
        return postHandler.createNew(course, errors);
    }
}
