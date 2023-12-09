package ma.emsi.leavemanagement.controllers;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import ma.emsi.leavemanagement.dtos.InputUserDto;
import ma.emsi.leavemanagement.entities.Employee;

/**
 * EmployeeController
 */
public interface EmployeeController {

    public ResponseEntity<?> replaceEmployee(Employee employee);
    public CollectionModel<EntityModel<Employee>> getEmployees();
    public EntityModel<Employee> getOneEmployee(Long id);
    public ResponseEntity<?> resetPassword(InputUserDto inputUserDto);
}