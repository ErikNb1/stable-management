package de.kisters.stablemanagement.util.security.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDto {

    private String accessToken;
    private AuthenticationDto authentication = new AuthenticationDto();

}
