package by.it.academy.adorop.web.security;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class StudentAuthenticationProvider implements AuthenticationProvider {
    private final StudentService studentService;

    @Autowired
    public StudentAuthenticationProvider(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Student student = retrieveStudent(authentication);
        verifyValidness(authentication, student);
        return getCustomAuthentication(student);
    }

    private Student retrieveStudent(Authentication authentication) {
        String documentId = authentication.getName();
        return studentService.getByDocumentId(documentId);
    }

    private void verifyValidness(Authentication authentication, Student student) {
        if (student == null) {
            throw new UsernameNotFoundException("user not found");
        }
        if (!authentication.getCredentials().equals(student.getPassword())) {
            throw new BadCredentialsException("bad credentials");
        }
    }

    //TODO name?
    private Authentication getCustomAuthentication(Student student) {
        Authentication studentAuthentication = StudentAuthentication.newInstance(student);
        studentAuthentication.setAuthenticated(true);
        return studentAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
