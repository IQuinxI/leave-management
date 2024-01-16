package ma.emsi.leavemanagement.repositories;


import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import ma.emsi.leavemanagement.enums.LeaveStatus;




public interface LeaveRepository extends JpaRepository<Leave, Long> {
    public List<Leave> findByPerson(Person person);
    List<Leave> findByStatusAndPerson(LeaveStatus status, Person person);
}