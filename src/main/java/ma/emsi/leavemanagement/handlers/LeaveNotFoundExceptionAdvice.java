package ma.emsi.leavemanagement.handlers;

import ma.emsi.leavemanagement.exceptions.LeaveNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LeaveNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleLeaveNotFoundException(LeaveNotFoundException ex) {
        return ex.getMessage();
    }


}


