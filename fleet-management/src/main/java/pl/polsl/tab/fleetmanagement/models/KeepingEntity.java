package pl.polsl.tab.fleetmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "keeping", schema = "public", catalog = "testdb")
public class KeepingEntity {
    private long id;
    private Date startdate;
    private Date enddate;
    private int peopleId;
    private int vehiclesId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "people_id")
    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    @Basic
    @Column(name = "vehicles_id")
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
}
