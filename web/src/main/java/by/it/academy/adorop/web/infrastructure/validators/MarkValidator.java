package by.it.academy.adorop.web.infrastructure.validators;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.api.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


//TODO validation.negative error code
@Component("markValidator")
public class MarkValidator implements Validator {

    private final CourseService courseService;
    private final StudentService studentService;
    private final MarkService markService;

    @Autowired
    public MarkValidator(CourseService courseService, StudentService studentService, MarkService markService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.markService = markService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Mark.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        verifyOnEmptiness(errors);
        Mark markRequest = (Mark) target;
        if (requestIsForCreating(markRequest)) {
            Student student = markRequest.getStudent();
            if (areRequiredFieldsNotNull(student)) {
                Long studentId = student.getId();
                verifyIfRequiredEntitiesExist(errors, studentId, studentService, "student");
            }
            Course course = markRequest.getCourse();
            if (areRequiredFieldsNotNull(course)) {
                Long courseId = course.getId();
                verifyIfRequiredEntitiesExist(errors, courseId, courseService, "course");
            }
        } else {
            validateNonUpdatableFields(errors, markRequest);
            validateValue(errors, markRequest);
        }
    }

    private boolean areRequiredFieldsNotNull(Course course) {
        return course != null && course.getId() != null;
    }

    private boolean areRequiredFieldsNotNull(Student student) {
        return student != null && student.getId() != null;
    }

    private void validateValue(Errors errors, Mark markRequest) {
        Integer value = markRequest.getValue();
        if (value == null || value < 0 || value > 10) {
            errors.reject("validation.negative");
        }
    }

    private void validateNonUpdatableFields(Errors errors, Mark markRequest) {
        Mark markFromDatabaseByGivenId = markService.find(markRequest.getId());
        if (areRequiredFieldsNotNull(markRequest.getCourse()) && areRequiredFieldsNotNull(markRequest.getStudent())) {
            verifyIds(errors, markFromDatabaseByGivenId, markRequest);
        } else {
            errors.reject("validation.negative");
        }
    }

    private void verifyIds(Errors errors, Mark markFromDatabaseByGivenId, Mark markRequest) {
        if (!getCourseId(markFromDatabaseByGivenId).equals(getCourseId(markRequest)) ||
                !getStudentId(markFromDatabaseByGivenId).equals(getStudentId(markRequest))) {
            errors.reject("validation.negative");
        }
    }

    private Long getCourseId(Mark mark) {
        return mark.getCourse().getId();
    }

    private Long getStudentId(Mark mark) {
        return mark.getStudent().getId();
    }

    private boolean requestIsForCreating(Mark mark) {
        return mark.getId() == null;
    }

    @SuppressWarnings("unchecked")
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
