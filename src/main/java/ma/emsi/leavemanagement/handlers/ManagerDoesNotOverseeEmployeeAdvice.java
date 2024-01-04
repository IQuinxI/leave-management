package ma.emsi.leavemanagement.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ma.emsi.leavemanagement.exceptions.ManagerDoesNotOverseeEmployeeException;

/**
 * ManagerDoesNotOverseeEmployeeAdvice
 */
@ControllerAdvice
public class ManagerDoesNotOverseeEmployeeAdvice {
    @ResponseBody
    @ExceptionHandler(ManagerDoesNotOverseeEmployeeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    String ManagerDoesNotOverseeEmployeeHandler(ManagerDoesNotOverseeEmployeeException ex) {
        return ex.getMessage();
    }
    
}