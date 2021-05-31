package pl.polsl.tab.fleetmanagement.auth;

import lombok.Getter;
import lombok.Setter;

public class JwtAuthenticationRequest {

    @Getter @Setter private String username;
    @Getter @Setter private String password;


    public JwtAuthenticationRequest() {
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
