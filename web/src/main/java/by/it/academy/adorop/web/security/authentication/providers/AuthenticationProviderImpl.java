package by.it.academy.adorop.web.security.authentication.providers;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.security.authentication.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final List<UserService> userServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProviderImpl(List<UserService> userServices, PasswordEncoder passwordEncoder) {
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
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
            user = (User) userService.findSingleResultBy("documentId", documentId);
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
        if (!isPasswordValid(authentication, user)) {
            throw new BadCredentialsException("bad credentials");
        }
    }

    private boolean isPasswordValid(Authentication authentication, User user) {
        return passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword());
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
