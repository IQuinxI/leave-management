package ma.emsi.leavemanagement.exceptions;

/**
 * LeaveInPendingAlreadyExistsException
 */
public class LeaveInPendingAlreadyExistsException extends RuntimeException{

    public LeaveInPendingAlreadyExistsException() {
        super("There's a leave in pending already");
    }
}