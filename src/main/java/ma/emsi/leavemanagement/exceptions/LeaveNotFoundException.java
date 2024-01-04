package ma.emsi.leavemanagement.exceptions;

public class LeaveNotFoundException extends RuntimeException{
    public LeaveNotFoundException(String message) {
        super(message);
    }

    public LeaveNotFoundException(Long idLeave) {
        super("Leave not found with id : " + idLeave);
    }

    public LeaveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
