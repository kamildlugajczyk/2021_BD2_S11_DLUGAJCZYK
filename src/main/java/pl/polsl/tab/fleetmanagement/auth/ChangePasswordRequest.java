package pl.polsl.tab.fleetmanagement.auth;

import lombok.Getter;

public class ChangePasswordRequest {
    @Getter private String oldPassword;
    @Getter private String newPassword;

}
