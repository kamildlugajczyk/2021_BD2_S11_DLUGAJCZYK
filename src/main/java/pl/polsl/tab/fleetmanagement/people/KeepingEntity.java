package pl.polsl.tab.fleetmanagement.people;

import pl.polsl.tab.fleetmanagement.exploitation.OperationCostEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "keeping", schema = "public", catalog = "testdb")
public class KeepingEntity {
    private long id;
    private Date startDate;
    private Date endDate;
    private int peopleId;
    private int vehiclesId;
    private PersonEntity personByPersonId;
    private VehiclesEntity vehiclesByVehiclesId;
    private Collection<OperationCostEntity> operationCostsById;

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
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startdate) {
        this.startDate = startdate;
    }

    @Basic
    @Column(name = "enddate", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date enddate) {
        this.endDate = enddate;
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

    @ManyToOne(fetch = FetchType.LAZY) //EDITEED
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PersonEntity getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(PersonEntity peopleByPeopleId) {
        this.personByPersonId = peopleByPeopleId;
    }

    @ManyToOne(fetch = FetchType.LAZY) //EDITED
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehiclesEntity getVehiclesByVehiclesId() {
        return vehiclesByVehiclesId;
    }

    public void setVehiclesByVehiclesId(VehiclesEntity vehiclesByVehiclesId) {
        this.vehiclesByVehiclesId = vehiclesByVehiclesId;
    }

    @OneToMany(mappedBy = "keepingByKeepingId")
    public Collection<OperationCostEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostEntity> operationCostsById) {
        this.operationCostsById = operationCostsById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeepingEntity that = (KeepingEntity) o;
        return id == that.id && peopleId == that.peopleId && vehiclesId == that.vehiclesId && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, peopleId, vehiclesId);
    }
}
