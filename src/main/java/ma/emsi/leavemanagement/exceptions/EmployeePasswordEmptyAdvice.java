package ma.emsi.leavemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmployeePasswordNotFoundAdvice
 */
@ControllerAdvice
public class EmployeePasswordEmptyAdvice {
    @ResponseBody
    @ExceptionHandler(EmployeePasswordIsEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String EmployeePasswordEmptyHandler(EmployeePasswordIsEmptyException ex) {
        return ex.getMessage();
    }
    
}