package ma.emsi.leavemanagement.exceptions;

/**
 * EmployeePasswordIsEmptyException
 */
public class FieldIsEmptyOrNullException extends RuntimeException {

    public FieldIsEmptyOrNullException() {
        super("Cannot pass an empty or null field");
    }

    public FieldIsEmptyOrNullException(String fieldName) {
        super("The field '" + fieldName + "' cannot be null");
    }
}