package de.kisters.stablemanagement.user.service;

import de.kisters.stablemanagement.user.model.dto.UserDto;
import de.kisters.stablemanagement.user.model.dto.LoginDto;
import de.kisters.stablemanagement.user.model.dto.UserEditDto;
import de.kisters.stablemanagement.user.model.dto.UserRegistrationDto;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.util.security.jwt.dto.JwtTokenDto;

import java.util.List;

public interface UserService {

    JwtTokenDto loginUser(LoginDto loginDto);
    UserDto registerUser(UserRegistrationDto registrationDto);
    UserDto getUserForToken();
    User getUserEntityForToken();
    boolean hasUserRole(User user, String role);
    JwtTokenDto refreshToken(String refreshToken);
    void save(User user);
    User getUserByEmail(String userName);
    User getUserById(int userId);
    UserDto getMappedDto(User reservedBy);
}
