package by.it.academy.adorop.web.filters;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.web.commands.Command;
import by.it.academy.adorop.web.commands.CommandsFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityTeachersFilter extends BasicFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        String operation = request.getParameter("operation");

        if (teacher == null && !"register".equals(operation) && !"saveUser".equals(operation)) {
            Command command = CommandsFactory.createAuthenticationCommand(request);
            command.execute(response);
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
