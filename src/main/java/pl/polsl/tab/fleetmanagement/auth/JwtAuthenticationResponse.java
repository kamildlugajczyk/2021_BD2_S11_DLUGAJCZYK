package pl.polsl.tab.fleetmanagement.auth;

import lombok.Getter;

public class JwtAuthenticationResponse {

    @Getter private final String jwt;

    public JwtAuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
