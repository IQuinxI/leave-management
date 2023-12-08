package ma.emsi.leavemanagement.exceptions;

/**
 * EmployeeNotFoundException
 */
public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Long id) {
        super("Could not find the employee with the id: " + id);
    }
}