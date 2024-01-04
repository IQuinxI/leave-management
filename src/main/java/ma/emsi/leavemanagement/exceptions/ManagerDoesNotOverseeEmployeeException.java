package ma.emsi.leavemanagement.exceptions;

/**
 * ManagerDoesNotOverseeEmployeeException
 */
public class ManagerDoesNotOverseeEmployeeException extends RuntimeException{

    public ManagerDoesNotOverseeEmployeeException() {
        super("This employee is overseen by another manager");
    }
}