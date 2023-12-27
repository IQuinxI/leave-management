package ma.emsi.leavemanagement.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ma.emsi.leavemanagement.exceptions.LeaveNotFoundException;

/**
 * LeaveNotFoundAdvice
 */
@ControllerAdvice
public class LeaveNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(LeaveNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String LeaveNotFoundHandler(LeaveNotFoundException ex) {
        return ex.getMessage();
    }
}