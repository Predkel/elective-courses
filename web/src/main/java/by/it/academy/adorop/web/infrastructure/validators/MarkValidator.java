package by.it.academy.adorop.web.infrastructure.validators;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.api.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("markValidator")
public class MarkValidator implements Validator {

    private CourseService courseService;
    private StudentService studentService;

    @Autowired
    public MarkValidator(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Mark.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        verifyOnEmptiness(errors);
        Mark mark = (Mark) target;
        Student student = mark.getStudent();
        Long studentId = student.getId();
        if (student != null && studentId != null) {
            verifyIfRequiredEntitiesExist(errors, studentId, studentService, "student");
        }
        Course course = mark.getCourse();
        Long courseId = course.getId();
        if (course != null && courseId != null) {
            verifyIfRequiredEntitiesExist(errors, courseId, courseService, "course");
        }
    }

    private void verifyIfRequiredEntitiesExist(Errors errors, Long id, Service courseService, String field) {
        if (courseService.find(id) == null) errors.rejectValue(field, "validation.negative");
    }

    private void verifyOnEmptiness(Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "student", "field.required");
        ValidationUtils.rejectIfEmpty(errors, "course", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "student.id", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "course.id", "field.required");
    }
}
