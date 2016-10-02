package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.get.GetHandler;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.post.PostHandler;
import by.it.academy.adorop.web.infrastructure.http.method.handlers.put.PutHandler;
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
import java.util.Map;

@Controller
@RequestMapping("/marks")
public class MarkController {
    private final Validator validator;
    private final PostHandler<Mark> postHandler;
    private final PutHandler<Mark> putHandler;
    private final GetHandler<Long> getHandler;

    @Autowired
    public MarkController(@Qualifier("markValidator") Validator validator,
                          @Qualifier("markPostHandler") PostHandler<Mark> postHandler,
                          @Qualifier("markPutHandler") PutHandler<Mark> putHandler,
                          @Qualifier("markGetHandler") GetHandler<Long> getHandler) {
        this.validator = validator;
        this.postHandler = postHandler;
        this.putHandler = putHandler;
        this.getHandler = getHandler;
    }

    @InitBinder
    public void setValidator(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(validator);
    }

    @RequestMapping
    public ResponseEntity getBy(@RequestParam Map<String, String> parameters) {
        return getHandler.getBy(parameters);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("#mark != null and #mark.student != null and #mark.student.equals(principal)")
    public ResponseEntity createNew(@RequestBody @Valid Mark mark, Errors errors) {
        return postHandler.createNew(mark, errors);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasPermission(#mark, 'update')")
    public ResponseEntity<Void> update(@RequestBody @Valid Mark mark, Errors errors) {
        return putHandler.update(mark, errors);
    }
}
