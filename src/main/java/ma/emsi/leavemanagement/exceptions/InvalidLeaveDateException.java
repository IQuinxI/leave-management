package ma.emsi.leavemanagement.exceptions;

public class InvalidLeaveDateException extends RuntimeException{
    public InvalidLeaveDateException(String message) {
        super(message);
    }

    public InvalidLeaveDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
