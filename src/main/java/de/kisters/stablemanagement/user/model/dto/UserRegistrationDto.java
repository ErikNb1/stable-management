package de.kisters.stablemanagement.user.model.dto;

import de.kisters.stablemanagement.user.model.entity.Role;
import de.kisters.stablemanagement.util.validation.registration.PasswordMatches;
import de.kisters.stablemanagement.util.validation.registration.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class UserRegistrationDto {
    @NotNull
    @NotEmpty
    private String username;
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;
    @NotNull
    private Role role;
}
