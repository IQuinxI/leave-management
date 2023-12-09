package ma.emsi.leavemanagement.services;

import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import ma.emsi.leavemanagement.dtos.InputUserDto;
import ma.emsi.leavemanagement.entities.Employee;

/**
 * EmployeeService
 */
public interface EmployeeService {

    public ResponseEntity<?> replaceEmployee(Employee employee);
    public CollectionModel<EntityModel<Employee>> getEmployees();
    public EntityModel<Employee> getEmployee(Long id);
    public ResponseEntity<?> resetPassword(InputUserDto inputUserDto);
}