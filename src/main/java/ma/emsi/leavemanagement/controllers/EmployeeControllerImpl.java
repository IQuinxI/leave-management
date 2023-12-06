package ma.emsi.leavemanagement.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ma.emsi.leavemanagement.entities.Employee;

/**
 * EmployeeControllerImpl
 */
@RestController
public class EmployeeControllerImpl implements EmployeeController{

    @Override
    public ResponseEntity<?> replaceEmployee(Employee employee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replaceEmployee'");
    }

    @Override
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployees'");
    }

    @Override
    public EntityModel<Employee> getEmployee(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployee'");
    }

    
}