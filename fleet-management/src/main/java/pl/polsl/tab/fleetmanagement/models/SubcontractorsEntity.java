package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subcontractors", schema = "public", catalog = "testdb")
public class SubcontractorsEntity {
    private long id;
    private String name;
    private String address;
    private String phonenumber;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phonenumber")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcontractorsEntity that = (SubcontractorsEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phonenumber);
    }
}
