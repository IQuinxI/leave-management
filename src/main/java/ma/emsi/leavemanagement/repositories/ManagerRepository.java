package ma.emsi.leavemanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.leavemanagement.entities.Manager;

/**
 * ManagerRepository
 */
public interface ManagerRepository extends JpaRepository<Manager, Long> {
   // List<Manager>  findByEmployees_Id(Long id);
      List<Manager>  findByIdAndEmployees_Id(Long idManager, Long idEmployee);

}