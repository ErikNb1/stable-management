package de.kisters.stablemanagement.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String userName, password, accessToken;
}
