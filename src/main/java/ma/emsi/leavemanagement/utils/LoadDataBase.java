package ma.emsi.leavemanagement.utils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import ma.emsi.leavemanagement.entities.auth.Role;
import ma.emsi.leavemanagement.entities.auth.User;
import ma.emsi.leavemanagement.enums.Approbation;
import ma.emsi.leavemanagement.enums.LeaveStatus;
import ma.emsi.leavemanagement.enums.LeaveType;
import ma.emsi.leavemanagement.repositories.auth.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.transaction.Transactional;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.entities.Manager;
import ma.emsi.leavemanagement.repositories.EmployeeRepository;
import ma.emsi.leavemanagement.repositories.LeaveRepository;
import ma.emsi.leavemanagement.repositories.ManagerRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * LoadDataBase
 */
@Configuration
public class LoadDataBase {
        @Transactional
        @Bean
        CommandLineRunner loadData(EmployeeRepository employeeRepository,
                        UserRepository userRepository,
                        LeaveRepository leaveRepository,
                        ManagerRepository managerRepository,
                        PasswordEncoder passwordEncoder) {
                return args -> {

                        // initializes the user accounts
                        userRepository.save(User.builder()
                                        .email("user3")
                                        .password(passwordEncoder.encode("user3"))
                                        .role(Role.USER)
                                        .build());

                        userRepository.save(User.builder()
                                        .email("wafa@gmail.com")
                                        .password(passwordEncoder.encode("123"))
                                        .role(Role.USER)
                                        .build());

                        userRepository.save(User.builder()
                                        .email("ad@gmail.com")
                                        .password(passwordEncoder.encode("admin"))
                                        .role(Role.ADMIN)
                                        .build());

                        userRepository.save(User.builder()
                                        .email("joey@gmail.com")
                                        .password(passwordEncoder.encode("joey"))
                                        .role(Role.ADMIN)
                                        .build());
                        userRepository.save(User.builder()
                                        .email("yassine@gmail.com")
                                        .password(passwordEncoder.encode("joey"))
                                        .role(Role.ADMIN)
                                        .build());

                        // intializes the managers accounts
                        managerRepository.save(Manager.builder()
                                        .firstName("fnManager1")
                                        .lastName("lnManager1")
                                        .phone("06348987435")
                                        .annualBalance(25)
                                        .unpaidBalance(140)
                                        .maternityBalance(190)
                                        .sickBalance(13)
                                        .salary(BigDecimal.valueOf(15000))
                                        .userAccount(userRepository.findById(2l).get())
                                        .build());
                        managerRepository.save(Manager.builder()
                                        .firstName("fnManager2")
                                        .lastName("lnManager2")
                                        .phone("06348987435")
                                        .annualBalance(25)
                                        .unpaidBalance(130)
                                        .maternityBalance(190)
                                        .sickBalance(13)
                                        .salary(BigDecimal.valueOf(15000))
                                        .userAccount(userRepository.findById(3l).get())
                                        .build());

                        // initializes the employees accounts
                        employeeRepository.save(Employee.builder()
                                        .firstName("firstname1")
                                        .lastName("lastname1")
                                        .phone("069729387")
                                        .annualBalance(20)
                                        .unpaidBalance(100)
                                        .maternityBalance(100)
                                        .sickBalance(10)
                                        .salary(BigDecimal.valueOf(10000))
                                        .poste("poste1")
                                        .manager(managerRepository.findById(1l).get())
                                        .userAccount(userRepository.findById(1l).get())
                                        .build());
                        employeeRepository.save(Employee.builder()
                                        .firstName("firstname2")
                                        .lastName("lastname2")
                                        .phone("069729387")
                                        .annualBalance(20)
                                        .unpaidBalance(100)
                                        .maternityBalance(100)
                                        .sickBalance(10)
                                        .salary(BigDecimal.valueOf(10000))
                                        .poste("poste2")
                                        .manager(managerRepository.findById(1l).get())
                                        .userAccount(userRepository.findById(4l).get())
                                        .build());

                        employeeRepository.save(Employee.builder()
                                        .firstName("firstname2")
                                        .lastName("lastname2")
                                        .phone("069729387")
                                        .annualBalance(20)
                                        .unpaidBalance(100)
                                        .maternityBalance(100)
                                        .sickBalance(10)
                                        .salary(BigDecimal.valueOf(10000))
                                        .poste("poste1")
                                        .manager(managerRepository.findById(2l).get())
                                        .userAccount(userRepository.findById(5l).get())
                                        .build());

                        // initializes the leave requests
                        leaveRepository.save(Leave.builder()
                                        .createdAt(Date.from(Instant.now()))
                                        .endDate(Date.from(Instant.now().plusSeconds(9000)))
                                        .startDate(Date.from(Instant.now().plusSeconds(3000)))
                                        .leaveType(LeaveType.UNPAID)
                                        .status(LeaveStatus.PENDING)
                                        .approbation(Approbation.APPRO_MANAGER)
                                        .person(employeeRepository.findById(3l).get())
                                        .build());
                        leaveRepository.save(Leave.builder()
                                        .createdAt(Date.from(Instant.now()))
                                        .endDate(Date.from(Instant.now().plusSeconds(9000)))
                                        .startDate(Date.from(Instant.now().plusSeconds(3000)))
                                        .leaveType(LeaveType.MATERNITY)
                                        .status(LeaveStatus.PENDING)
                                        .approbation(Approbation.APPRO_MANAGER)
                                        .person(employeeRepository.findById(4l).get())
                                        .build());

                        leaveRepository.save(Leave.builder()
                                        .createdAt(Date.from(Instant.now()))
                                        .endDate(Date.from(Instant.now().plusSeconds(9000)))
                                        .startDate(Date.from(Instant.now().plusSeconds(3000)))
                                        .leaveType(LeaveType.MATERNITY)
                                        .status(LeaveStatus.PENDING)
                                        .approbation(Approbation.APPRO_MANAGER)
                                        .person(employeeRepository.findById(5l).get())
                                        .build());
                        // // initializes the employees accounts
                        // employeeRepository.save(Employee.builder()
                        // .firstName("firstname1")
                        // .lastName("lastname1")
                        // .phone("069729387")
                        // .soldePaye(20)
                        // .soldeNonPaye(100)
                        // .soldeMaternité(100)
                        // .soldeMaladie(10)
                        // .salary(BigDecimal.valueOf(10000))
                        // .poste("poste1")
                        // .userAccount(userRepository.findById(1l).get())
                        // .build());
                        //
                        // employeeRepository.save(Employee.builder()
                        // .firstName("firstname2")
                        // .lastName("lastname2")
                        // .phone("069872312")
                        // .soldePaye(15)
                        // .soldeNonPaye(10)
                        // .soldeMaternité(75)
                        // .soldeMaladie(5)
                        // .salary(BigDecimal.valueOf(5000))
                        // .poste("poste2")
                        // .userAccount(userRepository.findById(2l).get())
                        // .build());
                        //
                };
        }
}