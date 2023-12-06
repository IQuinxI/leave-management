package ma.emsi.leavemanagement.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;

/**
 * EmployeeController
 */
public interface EmployeeController {

    public ResponseEntity<?> replaceEmployee(Employee employee);
    public CollectionModel<EntityModel<Employee>> getEmployees();
    public EntityModel<Employee> getEmployee(Long id);
}