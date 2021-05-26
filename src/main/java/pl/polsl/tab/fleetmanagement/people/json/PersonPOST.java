package pl.polsl.tab.fleetmanagement.people.json;

import lombok.Getter;

public class PersonPOST {
    @Getter private final String username;
    @Getter private final String password;
    @Getter private final String firstname;
    @Getter private final String lastname;
    @Getter private final String phoneNumber;
    @Getter private final Long functionId;

    public PersonPOST(String username, String password, String firstname, String lastname, String phoneNumber, Long functionId) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.functionId = functionId;
    }

    @Override
    public String toString() {
        return "PersonPOST{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", functionId=" + functionId +
                '}';
    }
}
