package pl.polsl.tab.fleetmanagement.people;

import lombok.Getter;
import pl.polsl.tab.fleetmanagement.function.FunctionEntity;

import java.util.Collection;

@Getter
public class PersonDTO {

    private long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String function;
    private Collection<KeepingEntity> keepingsById;

    public PersonDTO(long id, String firstname, String lastname, String phoneNumber, FunctionEntity functionsEntity,
                     Collection<KeepingEntity> keepingsById) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.function = functionsEntity.getName();
        this.keepingsById = keepingsById;
    }
}