package pl.polsl.tab.fleetmanagement.person;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.person.function.FunctionDTO;
import pl.polsl.tab.fleetmanagement.person.function.FunctionEntity;


public class PersonDTO {

    @Getter private long id;
    @Getter private String username;
    @Getter private String firstname;
    @Getter private String lastname;
    @Getter private String phoneNumber;
    @Getter private FunctionDTO function;
    //private Collection<KeepingDTO> keeping = new ArrayList<>();
    //private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PersonDTO(long id, String username, String firstname, String lastname,
                     String phoneNumber, FunctionEntity functionEntity) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.function = new FunctionDTO(functionEntity);
    }

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.username = personEntity.getUsername();
        this.firstname = personEntity.getFirstname();
        this.lastname = personEntity.getLastname();
        this.phoneNumber = personEntity.getPhoneNumber();
        this.function = new FunctionDTO(personEntity.getFunctionsByFunctionsId());
    }
}
