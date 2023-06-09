package de.kisters.stablemanagement.util.security.jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PayloadDto {
    private LocalDateTime iat, exp;
    private String aud, jti, sub;
    private String iss = "stable-management";
}
