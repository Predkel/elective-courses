package by.it.academy.adorop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }
}
