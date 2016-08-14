package by.it.academy.adorop.web.controllers;

import by.it.academy.adorop.model.users.User;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.service.api.UserService;
import by.it.academy.adorop.web.utils.pagination.Pagination;
import by.it.academy.adorop.web.utils.pagination.PaginationContentPutter;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public abstract class AbstractUserController<T extends User> {

    private static final String USER_ALREADY_EXISTS_MESSAGE = "User with the same document id already exists";

    protected final CourseService courseService;
    protected final MarkService markService;

    protected AbstractUserController(CourseService courseService, MarkService markService) {
        this.courseService = courseService;
        this.markService = markService;
    }

    @RequestMapping
    @Pagination(bunchAttributeName = "courses")
    public String showCourses(HttpServletRequest request) {
        return "main" + getPathToController();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveNewUser(@ModelAttribute("user") @Valid T user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return sendToRegisterForm(user, model);
        }
        if (alreadyExists(user)) {
            return sendToRegisterFormWithMessage(user, model);
        }
        getUserService().persist(user);
        return redirectToMain();
    }

    private boolean alreadyExists(T user) {
        return getUserService().isAlreadyExists(user.getDocumentId());
    }

    private String sendToRegisterForm(T user, Model model) {
        model.addAttribute("user", user);
        return "registration";
    }

    private String sendToRegisterFormWithMessage(T user, Model model) {
        model.addAttribute("message", USER_ALREADY_EXISTS_MESSAGE);
        return sendToRegisterForm(user, model);
    }

    private String redirectToMain() {
        return "redirect:" + getPathToController();
    }

    protected abstract String getPathToController();

    protected abstract UserService<T> getUserService();
}
