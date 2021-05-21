package pl.polsl.tab.fleetmanagement.vehiclepurpose;

import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "purposes", schema = "public", catalog = "testdb")
public class PurposesEntity {
    private long id;
    private String name;
    private Collection<VehiclesEntity> vehiclesById;

    public PurposesEntity() {
    }

    public PurposesEntity(String name) {
        this.name = name;
    }

    public PurposesEntity(long id, String name) {
        this.id = id;
        this.name = name;
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
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "purposesByPurposesId", fetch = FetchType.LAZY)
    public Collection<VehiclesEntity> getVehiclesById() {
        return vehiclesById;
    }

    public void setVehiclesById(Collection<VehiclesEntity> vehiclesById) {
        this.vehiclesById = vehiclesById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurposesEntity that = (PurposesEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
