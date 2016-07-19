package by.it.academy.adorop.web.security.authentication.providers;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.web.security.authentication.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public AuthenticationProviderImpl(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = getUserByGivenParameters(authentication);
        verifyValidness(authentication, user);
        return getCustomAuthentication(user);
    }

    private User getUserByGivenParameters(Authentication authentication) {
        String documentId = authentication.getName();
        User user = teacherService.getByDocumentId(documentId);
        return user != null ? user : studentService.getByDocumentId(documentId);
    }

    private void verifyValidness(Authentication authentication, User user) {
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        if (!authentication.getCredentials().equals(user.getPassword())) {
            throw new BadCredentialsException("bad credentials");
        }
    }

    private Authentication getCustomAuthentication(User user) {
        UserAuthentication authentication = UserAuthentication.newInstance(user);
        authentication.setAuthenticated(true);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
