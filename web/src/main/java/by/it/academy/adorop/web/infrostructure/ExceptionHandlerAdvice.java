package by.it.academy.adorop.web.infrostructure;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    public static final String BAD_REQUEST_PAGE = "badRequest";
    private final Logger logger = Logger.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(ServiceException.class)
    public String catchServiceException(Exception e) {
        logger.error(e);
        return "error";
    }

    @ExceptionHandler(TypeMismatchException.class)
    public String catchTypeMismatchException(Exception e, Model model, HttpServletRequest request) {
        return sendToBadRequestPage(model, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String catchAccessDeniedException(Exception e, Model model, HttpServletRequest request) {
        return sendToBadRequestPage(model, request);
    }

    private String sendToBadRequestPage(Model model, HttpServletRequest request) {
        String pathToMain = definePathToMain(request);
        model.addAttribute("pathToMain", pathToMain);
        return BAD_REQUEST_PAGE;
    }

    private String definePathToMain(HttpServletRequest request) {
        return request.getServletPath().contains("teachers") ? "/teachers" : "/students";
    }
}
