package pl.polsl.tab.fleetmanagement.people;

import pl.polsl.tab.fleetmanagement.function.FunctionsEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.model.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "people", schema = "public", catalog = "testdb")
public class PeopleEntity {
    private long id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private int functionsId;
    private Collection<KeepingEntity> keepingsById;
    private FunctionsEntity functionsByFunctionsId;
    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "firstname", nullable = false, length = 50)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", nullable = false, length = 50)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "phonenumber", nullable = false, length = 50)
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "functions_id", nullable = false)
    public int getFunctionsId() {
        return functionsId;
    }

    public void setFunctionsId(int functionsId) {
        this.functionsId = functionsId;
    }

    @OneToMany(mappedBy = "peopleByPeopleId")
    public Collection<KeepingEntity> getKeepingsById() {
        return keepingsById;
    }

    public void setKeepingsById(Collection<KeepingEntity> keepingsById) {
        this.keepingsById = keepingsById;
    }

    @ManyToOne
    @JoinColumn(name = "functions_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FunctionsEntity getFunctionsByFunctionsId() {
        return functionsByFunctionsId;
    }

    public void setFunctionsByFunctionsId(FunctionsEntity functionsByFunctionsId) {
        this.functionsByFunctionsId = functionsByFunctionsId;
    }

    @OneToMany(mappedBy = "peopleByPeopleId")
    public Collection<VehicleUnavailabilityEntity> getVehicleUnavailabilitiesById() {
        return vehicleUnavailabilitiesById;
    }

    public void setVehicleUnavailabilitiesById(Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById) {
        this.vehicleUnavailabilitiesById = vehicleUnavailabilitiesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeopleEntity that = (PeopleEntity) o;
        return id == that.id && functionsId == that.functionsId && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, phonenumber, functionsId);
    }
}
