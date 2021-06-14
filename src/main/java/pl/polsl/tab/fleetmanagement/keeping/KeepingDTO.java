package pl.polsl.tab.fleetmanagement.keeping;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleEntity;

import java.util.Date;

@Getter
public class KeepingDTO {

    private long id;
    @Getter @Setter private Date startdate;
    @Getter @Setter private Date enddate;
    @Getter @Setter private long peopleId;
    @Getter @Setter private long vehicleId;


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
}
