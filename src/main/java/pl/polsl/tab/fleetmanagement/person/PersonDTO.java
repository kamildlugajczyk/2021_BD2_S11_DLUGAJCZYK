package pl.polsl.tab.fleetmanagement.person;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.function.FunctionDTO;
import pl.polsl.tab.fleetmanagement.function.FunctionEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;

import java.util.ArrayList;
import java.util.Collection;


public class PersonDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private FunctionDTO function;
    //private Collection<KeepingEntity> keepingsById;
    //private Collection<KeepingDTO> keeping = new ArrayList<>();
    //private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PersonDTO(long id, String firstname, String lastname, String phoneNumber,
                     FunctionEntity functionEntity /*,Collection<KeepingEntity> keepingEntities*/) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.function = new FunctionDTO(functionEntity);
//        for (KeepingEntity keepingEntity : keepingEntities) {
//            this.keeping.add(new KeepingDTO(keepingEntity));
//        }
    }

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.firstname = personEntity.getFirstname();
        this.lastname = personEntity.getLastname();
        this.phoneNumber = personEntity.getPhoneNumber();
        this.function = new FunctionDTO(personEntity.getFunctionsByFunctionsId());
//        for (KeepingEntity keepingEntity : keepingEntities) {
//            this.keeping.add(new KeepingDTO(keepingEntity));
//        }
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

    public FunctionDTO getFunction() {
        return function;
    }
}
