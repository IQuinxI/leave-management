package ma.emsi.leavemanagement.validators;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.exceptions.EmployeeNotFoundException;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;

/**
 * EmployeeValidator
 */
@AllArgsConstructor
@Component
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    public Person doesEmployeeExist(Long idPerson) {
        return employeeRepository.findById(idPerson)
                .orElseThrow(() -> new EmployeeNotFoundException());
    }
}