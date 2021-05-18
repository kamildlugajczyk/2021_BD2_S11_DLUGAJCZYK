package pl.polsl.tab.fleetmanagement.model;

import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "keeping", schema = "public", catalog = "testdb")
public class KeepingEntity {
    private long id;
    private Date startdate;
    private Date enddate;
    private int peopleId;
    private int vehiclesId;
    private PeopleEntity peopleByPeopleId;
    private VehiclesEntity vehiclesByVehiclesId;
    private Collection<OperationCostsEntity> operationCostsById;

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
    @Column(name = "people_id", nullable = false)
    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    public int getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(int vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeepingEntity that = (KeepingEntity) o;
        return id == that.id && peopleId == that.peopleId && vehiclesId == that.vehiclesId && Objects.equals(startdate, that.startdate) && Objects.equals(enddate, that.enddate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startdate, enddate, peopleId, vehiclesId);
    }

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PeopleEntity getPeopleByPeopleId() {
        return peopleByPeopleId;
    }

    public void setPeopleByPeopleId(PeopleEntity peopleByPeopleId) {
        this.peopleByPeopleId = peopleByPeopleId;
    }

    @ManyToOne
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehiclesEntity getVehiclesByVehiclesId() {
        return vehiclesByVehiclesId;
    }

    public void setVehiclesByVehiclesId(VehiclesEntity vehiclesByVehiclesId) {
        this.vehiclesByVehiclesId = vehiclesByVehiclesId;
    }

    @OneToMany(mappedBy = "keepingByKeepingId")
    public Collection<OperationCostsEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostsEntity> operationCostsById) {
        this.operationCostsById = operationCostsById;
    }
}
