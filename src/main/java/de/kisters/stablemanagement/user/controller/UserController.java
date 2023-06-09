package de.kisters.stablemanagement.user.controller;

import de.kisters.stablemanagement.user.model.dto.LoginDto;
import de.kisters.stablemanagement.user.model.dto.UserDto;
import de.kisters.stablemanagement.user.model.dto.UserRegistrationDto;
import de.kisters.stablemanagement.user.model.entity.User;
import de.kisters.stablemanagement.user.service.UserService;
import de.kisters.stablemanagement.util.security.jwt.dto.JwtTokenDto;
import jakarta.servlet.Registration;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/authentication", "/user/login"})
    public JwtTokenDto login(@RequestBody @Valid final LoginDto loginDto) {
        return userService.loginUser(loginDto);
    }
    @PostMapping({"/registration"})
    public UserDto registration(@RequestBody @Valid final UserRegistrationDto registrationDto) {
        return userService.registerUser(registrationDto);
    }
    @GetMapping("/user/get-current")
    public UserDto getUserForToken() {
        return userService.getUserForToken();
    }
    @GetMapping("/user/refresh-token/{refreshToken}")
    public JwtTokenDto refreshToken(@PathVariable String refreshToken) {
        return userService.refreshToken(refreshToken);
    }
}
