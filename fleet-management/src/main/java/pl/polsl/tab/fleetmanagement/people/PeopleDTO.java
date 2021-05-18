package pl.polsl.tab.fleetmanagement.people;


import lombok.Getter;

@Getter
public class PeopleDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String function;
    //private Collection<KeepingEntity> keepingsById;
    //private FunctionsEntity functionsByFunctionsId;
    //private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PeopleDTO(long id, String firstname, String lastname, String phonenumber, FunctionsEntity functionsEntity) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.function = functionsEntity.getName();
    }
}
