package by.it.academy.adorop.web.infrastructure.http.method.handlers;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.api.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("studentPostHandler")
public class StudentPostHandler extends AbstractPostHandler<Student, Long> {
    private final StudentService studentService;

    @Autowired
    public StudentPostHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected Service<Student, Long> service() {
        return studentService;
    }
}
