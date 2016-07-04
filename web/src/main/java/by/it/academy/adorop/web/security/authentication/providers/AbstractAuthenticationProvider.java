package by.it.academy.adorop.web.security.authentication.providers;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.security.authentication.UserAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractAuthenticationProvider<T extends User> implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        T user = getUserByGivenParameters(authentication);
        verifyValidness(authentication, user);
        return getCustomAuthentication(user);
    }

    private T getUserByGivenParameters(Authentication authentication) {
        String documentId = authentication.getName();
        return getUserService().getByDocumentId(documentId);
    }

    private void verifyValidness(Authentication authentication, T user) {
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        if (!authentication.getCredentials().equals(user.getPassword())) {
            throw new BadCredentialsException("bad credentials");
        }
    }

    private Authentication getCustomAuthentication(T user) {
        UserAuthentication<T> authentication = UserAuthentication.newInstance(user);
        authentication.setAuthenticated(true);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    protected abstract UserService<T> getUserService();
}
