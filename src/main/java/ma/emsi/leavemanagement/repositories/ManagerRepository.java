package ma.emsi.leavemanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.leavemanagement.entities.Manager;

/**
 * ManagerRepository
 */
public interface ManagerRepository extends JpaRepository<Manager, Long> {
      List<Manager>  findByIdAndEmployees_Id(Long idManager, Long idEmployee);

}