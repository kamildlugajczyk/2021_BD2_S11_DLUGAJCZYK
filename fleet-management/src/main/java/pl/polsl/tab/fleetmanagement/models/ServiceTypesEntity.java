package pl.polsl.tab.fleetmanagement.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "service_types", schema = "public", catalog = "testdb")
public class ServiceTypesEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Getter
    @Setter
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    public ServiceTypesEntity(String name) {
        this.name = name;
    }

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
