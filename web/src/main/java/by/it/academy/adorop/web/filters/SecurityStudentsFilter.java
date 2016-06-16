package by.it.academy.adorop.web.filters;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.web.commands.Command;
import by.it.academy.adorop.web.commands.CommandsFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityStudentsFilter extends BasicFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Student student = (Student) request.getSession().getAttribute("student");
        String operation = request.getParameter("operation");

        if (student == null && !"register".equals(operation) && !"saveUser".equals(operation)) {
            Command command = CommandsFactory.createAuthenticationCommand(request);
            command.execute(request, response);
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
