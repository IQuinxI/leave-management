package ma.emsi.leavemanagement.utils;

import java.math.BigDecimal;

import ma.emsi.leavemanagement.entities.auth.Role;
import ma.emsi.leavemanagement.entities.auth.Userr;
import ma.emsi.leavemanagement.repositories.auth.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.transaction.Transactional;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * LoadDataBase
 */
@Configuration
public class LoadDataBase {
    @Transactional
    @Bean
    CommandLineRunner loadData(EmployeeRepository employeeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // initializes the user accounts
            userRepository.save(Userr.builder()
            .email("user@gmail.com")
            .password(passwordEncoder.encode("user"))
            .role(Role.USER)
            .build());

            userRepository.save(Userr.builder()
            .email("wafa@gmail.com")
            .password(passwordEncoder.encode("123"))
            .role(Role.USER)
            .build());

            userRepository.save(Userr.builder()
            .email("ad@gmail.com")
            .password(passwordEncoder.encode("admin"))
            .role(Role.ADMIN)
            .build());
            
            // initializes the employees accounts
            employeeRepository.save(Employee.builder()
                    .firstName("firstname1")
                    .lastName("lastname1")
                    .phone("069729387")
                    .soldePaye(20)
                    .soldeNonPaye(100)
                    .soldeMaternité(100)
                    .soldeMaladie(10)
                    .salary(BigDecimal.valueOf(10000))
                    .poste("poste1")
                    .userAccount(userRepository.findById(1l).get())
                    .build());

            employeeRepository.save(Employee.builder()
                    .firstName("firstname2")
                    .lastName("lastname2")
                    .phone("069872312")
                    .soldePaye(15)
                    .soldeNonPaye(10)
                    .soldeMaternité(75)
                    .soldeMaladie(5)
                    .salary(BigDecimal.valueOf(5000))
                    .poste("poste2")
                    .userAccount(userRepository.findById(2l).get())
                    .build());

        };
    }

}