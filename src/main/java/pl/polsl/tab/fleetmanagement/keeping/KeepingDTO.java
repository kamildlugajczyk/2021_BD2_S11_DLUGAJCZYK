package pl.polsl.tab.fleetmanagement.keeping;

import lombok.Getter;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleEntity;

import java.sql.Date;

@Getter
public class KeepingDTO {

    private long id;
    private Date startdate;
    private Date enddate;
    private long peopleId;
    private long vehicleId;


    public KeepingDTO(long id, Date startdate, Date enddate, PersonEntity personEntity, VehicleEntity vehicleEntity) {
        this.id = id;
        this.startdate = startdate;
        this.enddate = enddate;
        this.peopleId = personEntity.getId();
        this.vehicleId = vehicleEntity.getId();
    }

    public KeepingDTO(KeepingEntity keepingEntity) {
        this.id = keepingEntity.getId();
        this.startdate = keepingEntity.getStartdate();
        this.enddate = keepingEntity.getEnddate();
        this.peopleId = keepingEntity.getPeopleId();
        this.vehicleId = keepingEntity.getVehiclesId();
    }

    public Date getStartdate() {
        return startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public long getPeopleId() {
        return peopleId;
    }

    public long getVehicleId() {
        return vehicleId;
    }
}
