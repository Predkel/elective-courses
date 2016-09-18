package by.it.academy.adorop.web.security.authentication;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("ajaxRequestMatcher")
public class AjaxRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }
}
