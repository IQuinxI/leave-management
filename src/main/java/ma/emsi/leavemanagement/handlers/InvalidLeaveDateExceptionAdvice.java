package ma.emsi.leavemanagement.handlers;

import ma.emsi.leavemanagement.exceptions.InvalidLeaveDateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidLeaveDateExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidLeaveDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidLeaveDateException(InvalidLeaveDateException ex) {
        return ex.getMessage();
    }
}