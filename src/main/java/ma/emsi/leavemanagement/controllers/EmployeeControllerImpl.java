package ma.emsi.leavemanagement.controllers;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.dtos.InputUserDto;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.services.EmployeeService;

/**
 * EmployeeControllerImpl
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeControllerImpl implements EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    @Override
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    @Override
    public EntityModel<Employee> getOneEmployee(@PathVariable("id") Long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping
    @Override
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee employee) {
        return employeeService.replaceEmployee(employee);
    }

    @PostMapping("/{id}")
    @Override
    public ResponseEntity<?> resetPassword(@RequestBody InputUserDto inputUserDto) {
        return employeeService.resetPassword(inputUserDto);
    }
}