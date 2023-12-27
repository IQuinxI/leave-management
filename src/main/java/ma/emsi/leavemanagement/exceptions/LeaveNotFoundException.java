package ma.emsi.leavemanagement.exceptions;

/**
 * LeaveNotFoundException
 */
public class LeaveNotFoundException extends RuntimeException{

    public LeaveNotFoundException(Long idLeave) {
        super("Leave with the id "+ idLeave + " not found");
    }
    
}