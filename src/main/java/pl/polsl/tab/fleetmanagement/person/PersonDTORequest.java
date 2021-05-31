package pl.polsl.tab.fleetmanagement.person;


import lombok.Getter;

public class PersonDTORequest {

    @Getter private long id;
    @Getter private String username;
    @Getter private String password;
    @Getter private String firstname;
    @Getter private String lastname;
    @Getter private String phoneNumber;
    @Getter  private long functionId;

    public PersonDTORequest(long id, String username, String password, String firstname, String lastname,
                            String phoneNumber, long functionId ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.functionId = functionId;
    }

    public PersonDTORequest(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.username = personEntity.getUsername();
        this.password = personEntity.getPassword();
        this.firstname = personEntity.getFirstname();
        this.lastname = personEntity.getLastname();
        this.phoneNumber = personEntity.getPhoneNumber();
        this.functionId = personEntity.getFunctionsByFunctionsId().getId();
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
