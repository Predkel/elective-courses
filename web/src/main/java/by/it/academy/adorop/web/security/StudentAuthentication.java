package by.it.academy.adorop.web.security;

import by.it.academy.adorop.model.users.Student;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class StudentAuthentication implements Authentication {

    private final Student student;
    private boolean authenticated;

    private StudentAuthentication(Student student) {
        this.student = student;
    }

    public static Authentication newInstance(Student student) {
        return new StudentAuthentication(student);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public Object getCredentials() {
        return student.getPassword();
    }

    @Override
    public Object getDetails() {
        return student;
    }

    @Override
    public Object getPrincipal() {
        return student;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return student.getDocumentId();
    }
}
