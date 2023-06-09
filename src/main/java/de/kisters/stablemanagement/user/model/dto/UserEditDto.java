package de.kisters.stablemanagement.user.model.dto;

import de.kisters.stablemanagement.user.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDto extends UserDto {
    private String password;
    private Role role;

}
