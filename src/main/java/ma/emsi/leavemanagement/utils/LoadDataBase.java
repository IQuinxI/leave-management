package ma.emsi.leavemanagement.utils;

import java.math.BigDecimal;

import ma.emsi.leavemanagement.entities.auth.Role;
import ma.emsi.leavemanagement.entities.auth.User;
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
    CommandLineRunner loadData(EmployeeRepository employeeRepository) {
        return args -> {

            employeeRepository.save(Employee.builder()
                    .firstName("firstname1")
                    .lastName("lastname1")
                    .phone("069729387")
                    .soldePaye(20)
                    .soldeNonPaye(100)
                    .soldeMaternité(100)
                    .soldeMaladie(10)
                    .salire(BigDecimal.valueOf(10000))
                    .poste("poste1")
                    .email("email1@gmail.com")
                    .password("password1")
                    .build());

            employeeRepository.save(Employee.builder()
                    .firstName("firstname2")
                    .lastName("lastname2")
                    .phone("069872312")
                    .soldePaye(15)
                    .soldeNonPaye(10)
                    .soldeMaternité(75)
                    .soldeMaladie(5)
                    .salire(BigDecimal.valueOf(5000))
                    .poste("poste2")
                    .email("email2@gmail.com")
                    .password("password2")
                    .build());
        };
    }

    @Bean
    CommandLineRunner run(UserRepository repository, PasswordEncoder passwordEncoder){
        return args -> {
            repository.save(new User(1L, "user@gmail.com", passwordEncoder.encode("user"), Role.USER));
            repository.save(new User(2L, "wafa@gmail.com", passwordEncoder.encode("123"), Role.USER));
            repository.save(new User(2L, "ad@gmail.com", passwordEncoder.encode("admin"), Role.ADMIN));

        };
    }

}