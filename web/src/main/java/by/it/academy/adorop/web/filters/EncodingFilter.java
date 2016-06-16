package by.it.academy.adorop.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EncodingFilter extends BasicFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            request.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
