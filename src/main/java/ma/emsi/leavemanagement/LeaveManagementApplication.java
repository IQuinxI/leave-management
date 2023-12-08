package ma.emsi.leavemanagement;

import ma.emsi.leavemanagement.entities.auth.Role;
import ma.emsi.leavemanagement.entities.auth.Userr;
import ma.emsi.leavemanagement.repositories.auth.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LeaveManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveManagementApplication.class, args);
	}


}
