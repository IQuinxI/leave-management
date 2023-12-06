package ma.emsi.leavemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.leavemanagement.entities.Employee;

/**
 * EmployeeRepository
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    
}