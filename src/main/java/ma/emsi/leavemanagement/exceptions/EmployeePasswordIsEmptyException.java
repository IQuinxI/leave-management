package ma.emsi.leavemanagement.exceptions;

/**
 * EmployeePasswordIsEmptyException
 */
public class EmployeePasswordIsEmptyException extends RuntimeException {

    public EmployeePasswordIsEmptyException() {
        super("Empty password can not be set");
    }
}