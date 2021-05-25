package pl.polsl.tab.fleetmanagement.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "subcontractors", schema = "public", catalog = "testdb")
public class SubcontractorsEntity {
    private long id;
    private String name;
    private String address;
    private String phonenumber;
    private Collection<ServicingEntity> servicingsById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phonenumber", nullable = false, length = 50)
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

    @OneToMany(mappedBy = "subcontractorsBySubcontractorsId")
    public Collection<ServicingEntity> getServicingsById() {
        return servicingsById;
    }

    public void setServicingsById(Collection<ServicingEntity> servicingsById) {
        this.servicingsById = servicingsById;
    }
}
