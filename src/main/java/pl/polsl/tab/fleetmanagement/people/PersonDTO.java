package pl.polsl.tab.fleetmanagement.people;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.function.FunctionDTO;
import pl.polsl.tab.fleetmanagement.function.FunctionEntity;


public class PersonDTO {

    @Getter private long id;
    @Getter private String firstname;
    @Getter private String lastname;
    @Getter private String phoneNumber;
    @Getter private String mail;
    @Getter private FunctionDTO function;
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
}
