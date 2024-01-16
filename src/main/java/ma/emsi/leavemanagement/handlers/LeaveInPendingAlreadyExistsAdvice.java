package ma.emsi.leavemanagement.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ma.emsi.leavemanagement.exceptions.LeaveInPendingAlreadyExistsException;

/**
 * LeaveInPendingAlreadyExistsAdvice
 */
@ControllerAdvice
public class LeaveInPendingAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(LeaveInPendingAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String LeaveInPendingAlreadyExistsHandler(LeaveInPendingAlreadyExistsException ex) {
        return ex.getMessage();
    }
}