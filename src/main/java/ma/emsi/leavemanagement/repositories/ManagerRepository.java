package ma.emsi.leavemanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.emsi.leavemanagement.entities.Manager;
import ma.emsi.leavemanagement.entities.Person;

/**
 * ManagerRepository
 */
public interface ManagerRepository extends JpaRepository<Manager, Long> {
      List<Manager>  findByIdAndEmployees_Id(Long idManager, Long idEmployee);
      // List<Employee> findBy
      @Query("SELECT employees from Manager")
      List<Manager>  findTest();
}