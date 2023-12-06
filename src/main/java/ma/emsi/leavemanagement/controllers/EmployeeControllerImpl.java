package ma.emsi.leavemanagement.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.services.EmployeeService;

/**
 * EmployeeControllerImpl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeControllerImpl implements EmployeeController{
    private final EmployeeService employeeService;

    @GetMapping()
    @Override
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        return employeeService.getEmployees();
    }

    @Override
    public EntityModel<Employee> getEmployee(Long id) {
        return employeeService.getEmployee(id);
    }

    
    @Override
    public ResponseEntity<?> replaceEmployee(Employee employee) {
        return employeeService.replaceEmployee(employee);
    }
}