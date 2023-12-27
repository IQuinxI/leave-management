package ma.emsi.leavemanagement.exceptions;

public class LeaveNotFoundException extends RuntimeException{
    public LeaveNotFoundException(String message) {
        super(message);
    }

    public LeaveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
