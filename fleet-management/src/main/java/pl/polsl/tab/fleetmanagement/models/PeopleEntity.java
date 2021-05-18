package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "people", schema = "public", catalog = "testdb")
public class PeopleEntity {
    private long id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private int functionsId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "phonenumber")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "functions_id")
    public int getFunctionsId() {
        return functionsId;
    }

    public void setFunctionsId(int functionsId) {
        this.functionsId = functionsId;
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
