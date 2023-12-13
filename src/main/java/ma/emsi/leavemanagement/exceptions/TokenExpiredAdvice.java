package ma.emsi.leavemanagement.exceptions;

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