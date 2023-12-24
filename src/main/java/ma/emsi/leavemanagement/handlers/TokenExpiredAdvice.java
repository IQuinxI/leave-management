package ma.emsi.leavemanagement.handlers;

import ma.emsi.leavemanagement.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TokenExpiredAdvice
 */
@ControllerAdvice
public class TokenExpiredAdvice {
    @ResponseBody
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String TokenExpiredHandker(TokenExpiredException ex) {
        return ex.getMessage();
    }
    
}