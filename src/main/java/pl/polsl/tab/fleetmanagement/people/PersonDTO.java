package pl.polsl.tab.fleetmanagement.people;


import lombok.Getter;
import pl.polsl.tab.fleetmanagement.function.FunctionDTO;
import pl.polsl.tab.fleetmanagement.function.FunctionEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;

import java.util.ArrayList;
import java.util.Collection;


@Getter
public class PersonDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private FunctionDTO function;
    //private Collection<KeepingEntity> keepingsById;
    private Collection<KeepingDTO> keeping = new ArrayList<>();
    //private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PersonDTO(Long id, String firstname, String lastname, String phonenumber,
                     FunctionEntity functionEntity, Collection<KeepingEntity> keepingEntities) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.function = new FunctionDTO(functionEntity);
        for (KeepingEntity keepingEntity : keepingEntities) {
            this.keeping.add(new KeepingDTO(keepingEntity));
        }
    }
}
