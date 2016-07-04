package by.it.academy.adorop.web.security.authentication.providers;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentAuthenticationProviderImpl extends AbstractAuthenticationProvider<Student> implements StudentAuthenticationProvider {

    private final StudentService studentService;

    @Autowired
    public StudentAuthenticationProviderImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected UserService<Student> getUserService() {
        return studentService;
    }
}
