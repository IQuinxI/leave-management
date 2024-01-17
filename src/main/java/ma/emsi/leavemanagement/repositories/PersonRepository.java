package ma.emsi.leavemanagement.repositories;

import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.LeaveStatus;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findByLeaves_Status(LeaveStatus status);

}
