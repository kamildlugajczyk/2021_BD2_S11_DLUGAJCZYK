package pl.polsl.tab.fleetmanagement.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "vehicle_unavailability", schema = "public", catalog = "testdb")
public class VehicleUnavailabilityEntity {
    private long id;
    private Date startdate;
    private Date enddate;
    private String business;
    private int vehiclesId;
    private int peopleId;
    private Collection<ServiceRequestEntity> serviceRequestsById;
    private Collection<VehicleRentingsEntity> vehicleRentingsById;
    private VehiclesEntity vehiclesByVehiclesId;
    private PeopleEntity peopleByPeopleId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "business", nullable = false, length = 1)
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    public int getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(int vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    @Basic
    @Column(name = "people_id", nullable = false)
    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleUnavailabilityEntity that = (VehicleUnavailabilityEntity) o;
        return id == that.id && vehiclesId == that.vehiclesId && peopleId == that.peopleId && Objects.equals(startdate, that.startdate) && Objects.equals(enddate, that.enddate) && Objects.equals(business, that.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startdate, enddate, business, vehiclesId, peopleId);
    }

    @OneToMany(mappedBy = "vehicleUnavailabilityByVehicleUnavailabilityId")
    public Collection<ServiceRequestEntity> getServiceRequestsById() {
        return serviceRequestsById;
    }

    public void setServiceRequestsById(Collection<ServiceRequestEntity> serviceRequestsById) {
        this.serviceRequestsById = serviceRequestsById;
    }

    @OneToMany(mappedBy = "vehicleUnavailabilityByVehicleUnavailabilityId")
    public Collection<VehicleRentingsEntity> getVehicleRentingsById() {
        return vehicleRentingsById;
    }

    public void setVehicleRentingsById(Collection<VehicleRentingsEntity> vehicleRentingsById) {
        this.vehicleRentingsById = vehicleRentingsById;
    }

    @ManyToOne
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehiclesEntity getVehiclesByVehiclesId() {
        return vehiclesByVehiclesId;
    }

    public void setVehiclesByVehiclesId(VehiclesEntity vehiclesByVehiclesId) {
        this.vehiclesByVehiclesId = vehiclesByVehiclesId;
    }

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PeopleEntity getPeopleByPeopleId() {
        return peopleByPeopleId;
    }

    public void setPeopleByPeopleId(PeopleEntity peopleByPeopleId) {
        this.peopleByPeopleId = peopleByPeopleId;
    }
}
