package pl.polsl.tab.fleetmanagement.rentings;

import pl.polsl.tab.fleetmanagement.exploitation.OperationCostEntity;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "vehicle_rentings", schema = "public", catalog = "testdb")
public class VehicleRentingEntity {
    private long id;
    private int startmileage;
    private int endmileage;
    private boolean isbusiness;
    private int vehicleUnavailabilityId;
    private Collection<OperationCostEntity> operationCostsById;
    private VehicleUnavailabilityEntity vehicleUnavailabilityByVehicleUnavailabilityId;

    public VehicleRentingEntity(int startmileage, int endmileage, boolean isbusiness, int vehicleUnavailabilityId) {
        this.startmileage = startmileage;
        this.endmileage = endmileage;
        this.isbusiness = isbusiness;
        this.vehicleUnavailabilityId = vehicleUnavailabilityId;
    }

    public VehicleRentingEntity() {

    }

    @Override
    public String toString() {
        return "VehicleRentingsEntity{" +
                "id=" + id +
                ", startmileage=" + startmileage +
                ", endmileage=" + endmileage +
                ", isbusiness='" + isbusiness + '\'' +
                ", vehicleUnavailabilityId=" + vehicleUnavailabilityId +
                '}';
    }

    @Id
    @SequenceGenerator(
            name = "vehicle_rentings_id_seq",
            sequenceName = "vehicle_rentings_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vehicle_rentings_id_seq"
    )
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "startmileage", nullable = false)
    public int getStartmileage() {
        return startmileage;
    }

    public void setStartmileage(int startmileage) {
        this.startmileage = startmileage;
    }

    @Basic
    @Column(name = "endmileage", nullable = false)
    public int getEndmileage() {
        return endmileage;
    }

    public void setEndmileage(int endmileage) {
        this.endmileage = endmileage;
    }

    @Basic
    @Column(name = "isbusiness", nullable = false, length = 1)
    public boolean getIsbusiness() {
        return isbusiness;
    }

    public void setIsbusiness(boolean isbusiness) {
        this.isbusiness = isbusiness;
    }

    @Basic
    @Column(name = "vehicle_unavailability_id", nullable = false)
    public int getVehicleUnavailabilityId() {
        return vehicleUnavailabilityId;
    }

    public void setVehicleUnavailabilityId(int vehicleUnavailabilityId) {
        this.vehicleUnavailabilityId = vehicleUnavailabilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleRentingEntity that = (VehicleRentingEntity) o;
        return id == that.id && startmileage == that.startmileage && endmileage == that.endmileage && vehicleUnavailabilityId == that.vehicleUnavailabilityId && Objects.equals(isbusiness, that.isbusiness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startmileage, endmileage, isbusiness, vehicleUnavailabilityId);
    }

    @OneToMany(mappedBy = "vehicleRentingsByVehicleRentingsId")
    public Collection<OperationCostEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostEntity> operationCostsById) {
        this.operationCostsById = operationCostsById;
    }

    @ManyToOne
    @JoinColumn(name = "vehicle_unavailability_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehicleUnavailabilityEntity getVehicleUnavailabilityByVehicleUnavailabilityId() {
        return vehicleUnavailabilityByVehicleUnavailabilityId;
    }

    public void setVehicleUnavailabilityByVehicleUnavailabilityId(VehicleUnavailabilityEntity vehicleUnavailabilityByVehicleUnavailabilityId) {
        this.vehicleUnavailabilityByVehicleUnavailabilityId = vehicleUnavailabilityByVehicleUnavailabilityId;
    }
}
