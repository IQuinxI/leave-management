package ma.emsi.leavemanagement.handlers;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveDateException;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidLeaveTypeExceptionAdvice {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidLeaveTypeException(InvalidLeaveTypeException ex) {
        return ex.getMessage();
    }
    }

