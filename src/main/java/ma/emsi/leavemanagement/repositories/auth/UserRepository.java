package ma.emsi.leavemanagement.repositories.auth;

import ma.emsi.leavemanagement.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

}
