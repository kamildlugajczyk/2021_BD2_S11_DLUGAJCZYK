package pl.polsl.tab.fleetmanagement.person;


import pl.polsl.tab.fleetmanagement.function.FunctionDTO;
import pl.polsl.tab.fleetmanagement.function.FunctionEntity;


public class PersonDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String mail;
    private FunctionDTO function;
    //private Collection<KeepingDTO> keeping = new ArrayList<>();
    //private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PersonDTO(long id, String firstname, String lastname, String phoneNumber,
                     String mail, FunctionEntity functionEntity) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.function = new FunctionDTO(functionEntity);
    }

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.firstname = personEntity.getFirstname();
        this.lastname = personEntity.getLastname();
        this.phoneNumber = personEntity.getPhoneNumber();
        this.mail = personEntity.getMail();
        this.function = new FunctionDTO(personEntity.getFunctionsByFunctionsId());
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

    public String getMail() {
        return mail;
    }


    public FunctionDTO getFunction() {
        return function;
    }
}
