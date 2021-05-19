package pl.polsl.tab.fleetmanagement.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subcontractors", schema = "public", catalog = "testdb")
public class SubcontractorsEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Getter
    @Setter
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Getter
    @Setter
    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Basic
    @Getter
    @Setter
    @Column(name = "phonenumber", nullable = false, length = 50)
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcontractorsEntity that = (SubcontractorsEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phoneNumber);
    }

    @Override
    public String toString() {
        return "SubcontractorsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
