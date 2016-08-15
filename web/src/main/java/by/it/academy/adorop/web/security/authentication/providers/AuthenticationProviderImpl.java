package by.it.academy.adorop.web.security.authentication.providers;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.api.StudentService;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.security.authentication.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private List<UserService> userServices;

    @Autowired
    public AuthenticationProviderImpl(List<UserService> userServices) {
        this.userServices = userServices;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = getUserByGivenParameters(authentication);
        verifyValidness(authentication, user);
        return getCustomAuthentication(user);
    }

    private User getUserByGivenParameters(Authentication authentication) {
        String documentId = authentication.getName();
        User user = null;
        for (UserService userService : userServices) {
            user = userService.getByDocumentId(documentId);
            if (user != null) {
                break;
            }
        }
        return user;
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
