package pl.polsl.tab.fleetmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "service_types", schema = "public", catalog = "testdb")
public class ServiceTypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 50, unique = true)
    @Getter @Setter private String name;

    public ServiceTypesEntity(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "serviceTypes")
    @JsonIgnoreProperties("serviceTypes")
    @Getter @Setter private Set<ServicingEntity> servicingEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceTypesEntity that = (ServiceTypesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ServiceTypesEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
