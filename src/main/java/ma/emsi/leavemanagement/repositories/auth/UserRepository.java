package ma.emsi.leavemanagement.repositories.auth;

import ma.emsi.leavemanagement.entities.auth.Userr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Userr, Long> {
    Optional<Userr> findByEmail(String email);


}
