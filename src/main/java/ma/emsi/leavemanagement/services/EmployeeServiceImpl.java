package ma.emsi.leavemanagement.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.assemblers.EmployeeAssembler;
import ma.emsi.leavemanagement.controllers.EmployeeControllerImpl;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.exceptions.EmployeeNotFoundException;
import ma.emsi.leavemanagement.exceptions.EmployeePasswordIsEmptyException;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;
import ma.emsi.leavemanagement.utils.EmailServiceImpl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * EmployeeServiceImpl
 */
@AllArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeAssembler employeeAssembler;
    private final EmailServiceImpl emailServiceImpl;

    @Override
    public ResponseEntity<?> replaceEmployee(Employee newEmployee) {
        Employee updatedEmployee = employeeRepository.findById(newEmployee.getId())
                .map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setPhone(newEmployee.getPhone());
                    employee.setSoldePaye(newEmployee.getSoldePaye());
                    employee.setSoldeNonPaye(newEmployee.getSoldeNonPaye());
                    employee.setSoldeMaternité(newEmployee.getSoldeMaternité());
                    employee.setSoldeMaladie(newEmployee.getSoldeMaladie());
                    employee.setSalire(newEmployee.getSalire());
                    employee.setPoste(newEmployee.getPoste());

                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    return employeeRepository.save(newEmployee);
                });

        EntityModel<Employee> employeeEntityModel = employeeAssembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }

    @Override
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        List<EntityModel<Employee>> employeesCollectionModel = employeeRepository
                .findAll()
                .stream()
                .map(employeeAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employeesCollectionModel,
                linkTo(methodOn(EmployeeControllerImpl.class).getEmployees()).withSelfRel());
    }

    @Override
    public EntityModel<Employee> getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeAssembler.toModel(employee);
    }

    @Override
    public ResponseEntity<?> resetPassword(Long id, Map<String, String> password) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        String passwordValue = password.get("password").trim();

        if(passwordValue.isEmpty() || passwordValue == null) 
            throw new EmployeePasswordIsEmptyException();

        employee.setPassword(passwordValue);
        // employeeRepository.save(employee);

        EntityModel<Employee> employeeEntityModel = employeeAssembler.toModel(employee);

        emailServiceImpl.sendPasswordVerificationEmail("aqwzsxcv123@gmail.com", "Password Reset", """
                Hello User,
                This is a test of the Leave management system.
                Would you kindly ignore this message.
                I'm surprised you read this far.
                
                """);

        return ResponseEntity
                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }

}