package pl.polsl.tab.fleetmanagement.subcontractor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "subcontractors", schema = "public", catalog = "testdb")
public class SubcontractorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 50, unique = true)
    @Getter @Setter private String name;

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    @Getter @Setter private String address;

    @Basic
    @Column(name = "phonenumber", nullable = false, length = 50)
    @Getter @Setter private String phoneNumber;

    @OneToMany(mappedBy = "subcontractors")
    @JsonIgnoreProperties("subcontractors")
    @Getter @Setter public Set<ServicingEntity> servicingEntities;

    public SubcontractorEntity(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcontractorEntity that = (SubcontractorEntity) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(phoneNumber, that.phoneNumber);
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
                ", servicingEntities=" + servicingEntities +
                '}';
    }
}
