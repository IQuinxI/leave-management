package ma.emsi.leavemanagement.exceptions;

/**
 * LeaveRequestIsClosedException
 */
public class LeaveRequestIsClosedException extends RuntimeException { 

    public LeaveRequestIsClosedException() {
        super("Leave request is already closed");
    }
}