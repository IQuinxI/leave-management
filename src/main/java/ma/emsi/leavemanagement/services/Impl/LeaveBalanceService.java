package ma.emsi.leavemanagement.services.Impl;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.repositories.PersonRepository;
import ma.emsi.leavemanagement.services.PersonService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaveBalanceService {
    private final PersonRepository personRepository;


    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateLeaveBalanceEachNewYear() {
        List<Person> employees = personRepository.findAll();
        employees.stream()
                .forEach(employee -> {
                    employee.setAnnualBalance(employee.getAnnualBalance()+22);
                    employee.setSickBalance(4);
                    employee.setUnpaidBalance(30);
                    employee.setMaternityBalance(90);
                    personRepository.save(employee);
                });
    }
}
