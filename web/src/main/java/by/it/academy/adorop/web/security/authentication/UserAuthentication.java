package by.it.academy.adorop.web.security.authentication;

import by.it.academy.adorop.model.users.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class UserAuthentication<T extends User> implements Authentication {

    private final T user;
    private boolean authenticated;

    private UserAuthentication(T user) {
        this.user = user;
    }

    public static<T extends User> UserAuthentication<T> newInstance(T user) {
        return new UserAuthentication<>(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
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
        return user.getDocumentId();
    }
}
