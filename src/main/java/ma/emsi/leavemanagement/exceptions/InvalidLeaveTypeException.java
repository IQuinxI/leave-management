package ma.emsi.leavemanagement.exceptions;

public class InvalidLeaveTypeException extends RuntimeException {
    public InvalidLeaveTypeException(String message) {
        super(message);
    }
    public InvalidLeaveTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
