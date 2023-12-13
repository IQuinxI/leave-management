package ma.emsi.leavemanagement.exceptions;

/**
 * TokenExpiredException
 */
public class TokenExpiredException extends  RuntimeException{

    public TokenExpiredException() {
        super("The reset token has expired try again");
    }
}