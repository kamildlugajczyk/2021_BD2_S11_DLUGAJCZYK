package pl.polsl.tab.fleetmanagement.rentings;

import pl.polsl.tab.fleetmanagement.model.OperationCostsEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "vehicle_rentings", schema = "public", catalog = "testdb")
public class VehicleRentingsEntity {
    private long id;
    private int startmileage;
    private int endmileage;
    private Date startdate;
    private Date enddate;
    private String isbusiness;
    private int vehicleUnavailabilityId;
    private Collection<OperationCostsEntity> operationCostsById;
    private VehicleUnavailabilityEntity vehicleUnavailabilityByVehicleUnavailabilityId;

    public VehicleRentingsEntity(int startmileage, int endmileage, Date startdate, Date enddate, String isbusiness, int vehicleUnavailabilityId) {
        this.startmileage = startmileage;
        this.endmileage = endmileage;
        this.startdate = startdate;
        this.enddate = enddate;
        this.isbusiness = isbusiness;
        this.vehicleUnavailabilityId = vehicleUnavailabilityId;
    }

    public VehicleRentingsEntity() {

    }

    @Id
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
    @Column(name = "startdate", nullable = false)
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "enddate", nullable = false)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "isbusiness", nullable = false, length = 1)
    public String getIsbusiness() {
        return isbusiness;
    }

    public void setIsbusiness(String isbusiness) {
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
        VehicleRentingsEntity that = (VehicleRentingsEntity) o;
        return id == that.id && startmileage == that.startmileage && endmileage == that.endmileage && vehicleUnavailabilityId == that.vehicleUnavailabilityId && Objects.equals(startdate, that.startdate) && Objects.equals(enddate, that.enddate) && Objects.equals(isbusiness, that.isbusiness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startmileage, endmileage, startdate, enddate, isbusiness, vehicleUnavailabilityId);
    }

    @OneToMany(mappedBy = "vehicleRentingsByVehicleRentingsId")
    public Collection<OperationCostsEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostsEntity> operationCostsById) {
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
