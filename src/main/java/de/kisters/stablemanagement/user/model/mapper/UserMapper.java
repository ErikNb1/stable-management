package de.kisters.stablemanagement.user.model.mapper;

import de.kisters.stablemanagement.user.model.dto.UserDto;
import de.kisters.stablemanagement.user.model.dto.UserEditDto;
import de.kisters.stablemanagement.user.model.dto.UserRegistrationDto;
import de.kisters.stablemanagement.user.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    private final HorseMapper horseMapper;

    public UserMapper(HorseMapper horseMapper) {
        this.horseMapper = horseMapper;
    }

    public UserDto mapUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setHorses(horseMapper.mapToDtos(user.getHorses()));
        return dto;
    }
    public User mapDtoToEntity(UserDto dto) {
        User user = new User();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setHorses(horseMapper.mapToEntities(dto.getHorses()));
        return user;
    }
    public void replaceFields(UserEditDto userDto,  User user) {}

    public List<User> mapToEntities(List<UserDto> members) {
        List<User> temps = new ArrayList<>();
        members.forEach(userDto -> temps.add(mapDtoToEntity(userDto)));
        return temps;
    }

    public List<UserDto> mapToDtos(List<User> dtos) {
        List<UserDto> temps = new ArrayList<>();
        dtos.forEach(user -> temps.add(mapUserToDto(user)));
        return temps;
    }

    public User mapRegistrationToEntity(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setUserName(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setRole(registrationDto.getRole());
        user.setHorses(new ArrayList<>());
        return user;
    }
}
