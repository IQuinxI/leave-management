package ma.emsi.leavemanagement.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.assemblers.EmployeeAssembler;
import ma.emsi.leavemanagement.controllers.EmployeeControllerImpl;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
/**
 * EmployeeServiceImpl
 */
@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeAssembler employeeAssembler;

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
        List<EntityModel<Employee>> employeesCollectionModel =  employeeRepository
        .findAll()
        .stream()
        .map(employeeAssembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(employeesCollectionModel, 
        linkTo(methodOn(EmployeeControllerImpl.class).getEmployees()).withSelfRel());
    }

    @Override
    public EntityModel<Employee> getEmployee(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployee'");
    }

}