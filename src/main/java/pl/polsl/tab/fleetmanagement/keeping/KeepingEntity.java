package pl.polsl.tab.fleetmanagement.keeping;

import pl.polsl.tab.fleetmanagement.model.OperationCostsEntity;
import pl.polsl.tab.fleetmanagement.people.PersonEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleEntity;

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
    private long peopleId;
    private long vehiclesId;
    private PersonEntity peopleByPeopleId;
    private VehicleEntity vehiclesByVehiclesId;
    private Collection<OperationCostsEntity> operationCostsById;

    public KeepingEntity() {
    }

    public KeepingEntity(Date startdate, Date enddate, long peopleId, PersonEntity personEntity,
                         long vehiclesId, VehicleEntity vehicleEntity) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.peopleId = peopleId;
        this.peopleByPeopleId = personEntity;
        this.vehiclesId = vehiclesId;
        this.vehiclesByVehiclesId = vehicleEntity;
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
    @Column(name = "startdate", nullable = false)
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "enddate", nullable = true)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "people_id", nullable = false)
    public long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(long peopleId) {
        this.peopleId = peopleId;
    }

    @Basic
    @Column(name = "vehicles_id", nullable = false)
    public long getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(long vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    @ManyToOne(fetch = FetchType.LAZY) //EDITEED
    @JoinColumn(name = "people_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public PersonEntity getPeopleByPeopleId() {
        return peopleByPeopleId;
    }

    public void setPeopleByPeopleId(PersonEntity peopleByPeopleId) {
        this.peopleByPeopleId = peopleByPeopleId;
    }

    @ManyToOne(fetch = FetchType.LAZY) //EDITED
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public VehicleEntity getVehiclesByVehiclesId() {
        return vehiclesByVehiclesId;
    }

    public void setVehiclesByVehiclesId(VehicleEntity vehiclesByVehiclesId) {
        this.vehiclesByVehiclesId = vehiclesByVehiclesId;
    }

    @OneToMany(mappedBy = "keepingByKeepingId")
    public Collection<OperationCostsEntity> getOperationCostsById() {
        return operationCostsById;
    }

    public void setOperationCostsById(Collection<OperationCostsEntity> operationCostsById) {
        this.operationCostsById = operationCostsById;
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
}
