package ma.emsi.leavemanagement.handlers;


import ma.emsi.leavemanagement.exceptions.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InsufficientBalanceExceptionAdvice {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String InsufficientBalanceHandler(InsufficientBalanceException ex){
      return ex.getMessage();
    }

}
