package pl.polsl.tab.fleetmanagement.people;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.function.FunctionDTO;
import pl.polsl.tab.fleetmanagement.function.FunctionsEntity;


@Getter
public class PeopleDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private FunctionDTO function;
    //private Collection<KeepingEntity> keepingsById;
    //private Collection<KeepingEntityDTO> keepingsById;
    //private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PeopleDTO(long id, String firstname, String lastname, String phonenumber, FunctionsEntity functionsEntity) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.function = new FunctionDTO(functionsEntity);
    }
}
