package pl.polsl.tab.fleetmanagement.person;

import pl.polsl.tab.fleetmanagement.function.FunctionEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.model.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "people", schema = "public", catalog = "testdb")
public class PersonEntity {
    private long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String mail;
    private String password;
    private long functionsId;
    private Collection<KeepingEntity> keepingsById;
    private FunctionEntity functionsByFunctionsId;
    private Collection<VehicleUnavailabilityEntity> vehicleUnavailabilitiesById;

    public PersonEntity() {
    }

    public PersonEntity(String firstname, String lastname, String phoneNumber,
                        String mail, String password, FunctionEntity functionsByFunctionsId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
        this.functionsId = functionsByFunctionsId.getId();
        this.functionsByFunctionsId = functionsByFunctionsId;
    }

    public PersonEntity(PersonDTO personDTO) {
        this.firstname = personDTO.getFirstname();
        this.lastname = personDTO.getLastname();
        this.phoneNumber = personDTO.getPhoneNumber();
        this.mail = personDTO.getMail();
        this.functionsId = personDTO.getFunction().getId();
        this.functionsByFunctionsId = new FunctionEntity(personDTO.getFunction().getId(), personDTO.getFunction().getName());
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "mail", nullable = false, length = 50)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "functions_id", nullable = false)
    public long getFunctionsId() {
        return functionsId;
    }

    public void setFunctionsId(long functionsId) {
        this.functionsId = functionsId;
    }

    @OneToMany(mappedBy = "peopleByPeopleId"/*, cascade = CascadeType.PERSIST*/)
    public Collection<KeepingEntity> getKeepingsById() {
        return keepingsById;
    }

    public void setKeepingsById(Collection<KeepingEntity> keepingsById) {
        this.keepingsById = keepingsById;
    }

    @ManyToOne
    @JoinColumn(name = "functions_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FunctionEntity getFunctionsByFunctionsId() {
        return functionsByFunctionsId;
    }

    public void setFunctionsByFunctionsId(FunctionEntity functionsByFunctionsId) {
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
        PersonEntity that = (PersonEntity) o;
        return id == that.id && functionsId == that.functionsId && Objects.equals(firstname, that.firstname)
                && Objects.equals(lastname, that.lastname) && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(mail, that.mail) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, phoneNumber, mail, password, functionsId);
    }
}
