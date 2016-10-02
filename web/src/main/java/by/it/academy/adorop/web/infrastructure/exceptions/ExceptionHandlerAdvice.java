package by.it.academy.adorop.web.infrastructure.exceptions;

import by.it.academy.adorop.service.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final Logger logger = Logger.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity catchServiceException(ServiceException e) {
        logger.error(e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
