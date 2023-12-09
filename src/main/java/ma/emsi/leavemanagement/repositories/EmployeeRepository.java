package ma.emsi.leavemanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.entities.auth.Userr;

/**
 * EmployeeRepository
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUserAccount(Userr userAccount);
    
}