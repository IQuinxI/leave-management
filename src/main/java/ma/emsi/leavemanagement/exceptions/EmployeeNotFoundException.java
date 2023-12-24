package ma.emsi.leavemanagement.exceptions;

/**
 * EmployeeNotFoundException
 */
public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Long id) {
        super("Could not find the employee with the id: " + id);
    }

    public EmployeeNotFoundException() {
        super("Employee not found");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}