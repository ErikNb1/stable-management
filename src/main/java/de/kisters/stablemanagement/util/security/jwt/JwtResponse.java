package de.kisters.stablemanagement.util.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtResponse {

    private final String jwt;
    private final String type = "Bearer";
    private final String username;
    private final List<String> roles;
    public JwtResponse(String jwt, String username, List<String> roles) {
        this.jwt = jwt;
        this.username = username;
        this.roles = roles;
    }
}
