package pl.polsl.tab.fleetmanagement.vehicle;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "types", schema = "public", catalog = "testdb")
public class TypesEntity {
    private long id;
    private String name;
    private Collection<VehiclesEntity> vehiclesById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypesEntity that = (TypesEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "typesByTypesId")
    public Collection<VehiclesEntity> getVehiclesById() {
        return vehiclesById;
    }

    public void setVehiclesById(Collection<VehiclesEntity> vehiclesById) {
        this.vehiclesById = vehiclesById;
    }
}
