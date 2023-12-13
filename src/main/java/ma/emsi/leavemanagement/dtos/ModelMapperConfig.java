package ma.emsi.leavemanagement.dtos;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ma.emsi.leavemanagement.entities.auth.User;

/**
 * ModelMapperConfig
 */
@Component
public class ModelMapperConfig {
    private final ModelMapper modelMapper = new ModelMapper();

    public InputUserDto fromUser(User user) {
        return this.modelMapper.map(user, InputUserDto.class);
    }
    
}