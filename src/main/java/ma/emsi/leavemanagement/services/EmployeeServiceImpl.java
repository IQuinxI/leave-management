package ma.emsi.leavemanagement.services;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.assemblers.EmployeeAssembler;
import ma.emsi.leavemanagement.controllers.EmployeeControllerImpl;
import ma.emsi.leavemanagement.dtos.InputUserDto;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.entities.auth.User;
import ma.emsi.leavemanagement.exceptions.EmployeeNotFoundException;
import ma.emsi.leavemanagement.exceptions.FieldIsEmptyOrNullException;
import ma.emsi.leavemanagement.exceptions.TokenExpiredException;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;
import ma.emsi.leavemanagement.repositories.auth.UserRepository;
import ma.emsi.leavemanagement.security.JwtService;
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
        // TODO: REFACTOR THE CODE SO THAT IT WORKS WITH ALL CHILDREN OF PERSON
        private final EmployeeRepository employeeRepository;
        private final UserRepository userRepository;
        private final EmployeeAssembler employeeAssembler;
        private final EmailServiceImpl emailServiceImpl;
        private final PasswordEncoder passwordEncoder;
        

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
                                        employee.setSalary(newEmployee.getSalary());
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
        public ResponseEntity<?> resetPassword(Map<String, String> email) {

                // retrieves the email from the json {"email": "xxxxxx@xxxx.xxx"}
                String emailValue = email.get("email").trim();

                // checks if the value is empty or Null
                // TODO: add a util class to check multiple variables (respect OCP)
                if ((emailValue.isBlank() || emailValue == null))
                        throw new FieldIsEmptyOrNullException();

                // find the user account by email, if it doesn't exist return a
                // "EmployeeNotFoundException"
                User userAccount = userRepository.findByEmail(emailValue)
                                .orElseThrow(() -> new EmployeeNotFoundException());



                // TODO: generate token and send it via mail
                // the token expires after 10 minutes

                userAccount.setResetToken(UUID.randomUUID().toString());
                userAccount.setResetTokenExpiryDate(Instant.now().plus(Duration.ofMinutes(10)));

                // userAccount.setPassword(passwordEncoder.encode(inputUserDto.getPassword()));
                // userAccount.setPassword(passwordEncoder.encode("test"));

                // // find the employee by account, if they don't exist return a
                // // "EmployeeNotFoundException"
                Employee employee = employeeRepository.findByUserAccount(userAccount)
                                .orElseThrow(() -> new EmployeeNotFoundException());

                EntityModel<Employee> employeeEntityModel = employeeAssembler.toModel(employee);
                System.out.println("localhost:8080/api/v1/employees/reset-password?token="+userAccount.getResetToken());
                // sends an email to the User
                // TODO: run this on a different Thread to not stop the app
                // emailServiceImpl.sendPasswordVerificationEmail("aqwzsxcv123@gmail.com", "Password Reset", """
                //                 Hello User,
                //                 This is a test of the Leave management system.
                //                 Would you kindly ignore this message.
                //                 I'm surprised you read this far.

                //                 """);

                // return a response of the updated code
                // the employee is being returned instead of the user for security reasons
                return ResponseEntity
                                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                .body(employeeEntityModel);
        }

        // This should be called when the reset page (the link in the email is cliked)
        // is loaded for the user GET
        @Override
        public ResponseEntity<?> verifyToken(String token) {
                //TODO: retrieve the user account using the token
                User userAccount = userRepository.findByResetToken(token)
                        .orElseThrow(() -> new EmployeeNotFoundException());

                //TODO: check if the token has expired 
                if(userAccount.getResetTokenExpiryDate().compareTo(Instant.now()) < 0) 
                        throw new TokenExpiredException();

                

                Employee employee = employeeRepository.findByUserAccount(userAccount)
                                .orElseThrow(() -> new EmployeeNotFoundException());

                EntityModel<Employee> employeeEntityModel = employeeAssembler.toModel(employee);

                 return ResponseEntity
                                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                .body(employeeEntityModel);
        }

        // This method is called after the submission(POST)  
        // it reverifies the cridentials and changes the password
        @Override
        public ResponseEntity<?> changePassword(InputUserDto inputUserDto, String token) {
                User userAccount = userRepository.findByResetToken(token)
                        .orElseThrow(() -> new EmployeeNotFoundException());

                if(userAccount.getResetTokenExpiryDate().compareTo(Instant.now()) < 0) 
                        throw new TokenExpiredException();


                userAccount.setPassword(inputUserDto.getPassword());
                // userRepository.save(userAccount);
                Employee employee = employeeRepository.findByUserAccount(userAccount)
                                .orElseThrow(() -> new EmployeeNotFoundException());

                EntityModel<Employee> employeeEntityModel = employeeAssembler.toModel(employee);

                 return ResponseEntity
                                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                .body(employeeEntityModel);
        }

}