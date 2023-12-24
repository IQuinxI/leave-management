package ma.emsi.leavemanagement.repositories;

import ma.emsi.leavemanagement.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
