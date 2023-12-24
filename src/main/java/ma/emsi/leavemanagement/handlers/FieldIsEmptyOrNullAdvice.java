package ma.emsi.leavemanagement.handlers;

import ma.emsi.leavemanagement.exceptions.FieldIsEmptyOrNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmployeePasswordNotFoundAdvice
 */
@ControllerAdvice
public class FieldIsEmptyOrNullAdvice {
    @ResponseBody
    @ExceptionHandler(FieldIsEmptyOrNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String EmployeePasswordEmptyHandler(FieldIsEmptyOrNullException ex) {
        return ex.getMessage();
    }
    
}