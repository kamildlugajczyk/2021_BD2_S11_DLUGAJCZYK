package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.sql.Date;
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

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "startmileage")
    public int getStartmileage() {
        return startmileage;
    }

    public void setStartmileage(int startmileage) {
        this.startmileage = startmileage;
    }

    @Basic
    @Column(name = "endmileage")
    public int getEndmileage() {
        return endmileage;
    }

    public void setEndmileage(int endmileage) {
        this.endmileage = endmileage;
    }

    @Basic
    @Column(name = "startdate")
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "enddate")
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "isbusiness")
    public String getIsbusiness() {
        return isbusiness;
    }

    public void setIsbusiness(String isbusiness) {
        this.isbusiness = isbusiness;
    }

    @Basic
    @Column(name = "vehicle_unavailability_id")
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
}
