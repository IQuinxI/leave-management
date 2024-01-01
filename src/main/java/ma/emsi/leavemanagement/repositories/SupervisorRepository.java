package ma.emsi.leavemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.leavemanagement.entities.Supervisor;

/**
 * SueprvisorRepository
 */
public interface SupervisorRepository extends JpaRepository<Supervisor, Long>{

    
}