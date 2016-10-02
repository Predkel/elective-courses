package by.it.academy.adorop.web.infrastructure.exceptions;

import org.springframework.beans.PropertyAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestedPropertyDoesNotExistException extends PropertyAccessException {
    public RequestedPropertyDoesNotExistException(Throwable cause) {
        super("No such field", cause);
    }

    @Override
    public String getErrorCode() {
        return "No such field";
    }
}
