package ma.emsi.leavemanagement.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ma.emsi.leavemanagement.exceptions.LeaveRequestIsClosedException;

/**
 * LeaveRequestIsClosedAdvice
 */
@ControllerAdvice
public class LeaveRequestIsClosedAdvice {

    @ResponseBody
    @ExceptionHandler(LeaveRequestIsClosedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String LeaveRequestIsClosedHandler(LeaveRequestIsClosedException ex) {
        return ex.getMessage();
    }
}