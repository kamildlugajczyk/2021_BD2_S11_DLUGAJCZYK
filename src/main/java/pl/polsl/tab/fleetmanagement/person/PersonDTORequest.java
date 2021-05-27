package pl.polsl.tab.fleetmanagement.person;


public class PersonDTORequest {

    private long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private long functionId;

    public PersonDTORequest(long id, String firstname, String lastname, String phoneNumber, long functionId ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.functionId = functionId;
    }

    public PersonDTORequest(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.firstname = personEntity.getFirstname();
        this.lastname = personEntity.getLastname();
        this.phoneNumber = personEntity.getPhoneNumber();
        this.functionId = personEntity.getFunctionsByFunctionsId().getId();
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getFunctionId() {
        return functionId;
    }
}
