package ma.emsi.leavemanagement.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.transaction.Transactional;
import ma.emsi.leavemanagement.dtos.InputUserDto;
import ma.emsi.leavemanagement.dtos.ModelMapperConfig;
import ma.emsi.leavemanagement.repositories.auth.UserRepository;

/**
 * printTests
 */
@Configuration
public class printTests {
    @Transactional
    @Bean

    CommandLineRunner runTests(ModelMapperConfig modelMapperConfig, UserRepository userRepository) {
        return args -> {
            InputUserDto inputUserDto = modelMapperConfig.fromUser(userRepository.findById(1l).get());
            System.out.println(inputUserDto.getEmail() + inputUserDto.getPassword());
        };
    }
    
}