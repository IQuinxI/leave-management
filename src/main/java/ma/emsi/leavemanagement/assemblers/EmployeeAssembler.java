package ma.emsi.leavemanagement.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ma.emsi.leavemanagement.controllers.EmployeeControllerImpl;
import ma.emsi.leavemanagement.entities.Employee;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * EmployeeAssembler
 */
@Component
public class EmployeeAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>>{

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee, 
        linkTo(methodOn(EmployeeControllerImpl.class).getOneEmployee(employee.getId())).withSelfRel(),
        linkTo(methodOn(EmployeeControllerImpl.class).getEmployees()).withRel("Employees")
        );
    }


    
}