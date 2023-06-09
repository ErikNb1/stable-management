package de.kisters.stablemanagement.util.security.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationDto {
    private String strategy = "local";
    private String accessToken, refreshToken;
    private PayloadDto payload = new PayloadDto();
}
