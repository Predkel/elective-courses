package by.it.academy.adorop.web.infrastructure.validators;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CourseValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Course.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Course course = (Course) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required");
        Teacher teacher = course.getTeacher();
        if (teacher == null) {
            errors.rejectValue("teacher", "field.required");
        } else {
            if (teacher.getId() == null) {
                errors.rejectValue("teacher.id", "field.required");
            }
            if (teacher.getDocumentId() == null) {
                errors.rejectValue("teacher.documentId", "field.required");
            }
        }
    }
}
