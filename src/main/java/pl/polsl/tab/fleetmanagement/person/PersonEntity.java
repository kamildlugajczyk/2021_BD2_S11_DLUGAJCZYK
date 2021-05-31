package pl.polsl.tab.fleetmanagement.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.person.function.FunctionEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "people", schema = "public", catalog = "testdb")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter private Long id;

    @Basic
    @JsonIgnore
    @Column(name = "username", nullable = false, unique = true)
    @Getter @Setter private String username;

    @Basic
    @JsonIgnore
    @Column(name = "password", nullable = false)
    @Getter @Setter String password;

    @Basic
    @Column(name = "firstname", nullable = false, length = 50)
    @Getter @Setter private String firstname;

    @Basic
    @Column(name = "lastname", nullable = false, length = 50)
    @Getter @Setter private String lastname;

    @Basic
    @Column(name = "phonenumber", nullable = false, length = 50)
    @Getter @Setter private String phoneNumber;

    @Basic
    @Column(name = "functions_id", nullable = false)
    @Getter @Setter private Long functionId;

    @OneToMany(mappedBy = "peopleByPeopleId")
    @Getter @Setter private Collection<KeepingEntity> keepingsById;

    @ManyToOne
    @JoinColumn(name = "functions_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter private FunctionEntity functionsByFunctionsId;

    @OneToMany(mappedBy = "peopleByPeopleId")
    @Getter @Setter private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;


    public PersonEntity(String username, String password, String firstname,
                        String lastname, String phoneNumber, FunctionEntity functionsByFunctionsId) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.functionId = functionsByFunctionsId.getId();
        this.functionsByFunctionsId = functionsByFunctionsId;

    }

    public PersonEntity(PersonDTO personDTO) {
        this.username = personDTO.getUsername();
        this.firstname = personDTO.getFirstname();
        this.lastname = personDTO.getLastname();
        this.phoneNumber = personDTO.getPhoneNumber();
        this.functionId = personDTO.getFunction().getId();
        this.functionsByFunctionsId = new FunctionEntity(personDTO.getFunction().getId(), personDTO.getFunction().getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return id.equals(that.id) && functionId.equals(that.functionId) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, phoneNumber, functionId);
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", functionId=" + functionId +
                ", keepingsById=" + keepingsById +
                ", functionsByFunctionsId=" + functionsByFunctionsId +
                ", vehicleUnavailabilitiesById=" + vehicleUnavailabilitiesById +
                '}';
    }
}
