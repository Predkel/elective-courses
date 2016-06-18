package by.it.academy.adorop.web.commands;

import javax.servlet.http.HttpServletResponse;

public interface Command {
    void execute(HttpServletResponse response);
}
