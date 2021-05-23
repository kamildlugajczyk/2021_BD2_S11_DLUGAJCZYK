package pl.polsl.tab.fleetmanagement.keeping;

import lombok.Getter;
import pl.polsl.tab.fleetmanagement.model.OperationCostsEntity;
import pl.polsl.tab.fleetmanagement.people.PeopleDTO;
import pl.polsl.tab.fleetmanagement.people.PeopleEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;
import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import java.sql.Date;
import java.util.Collection;

@Getter
public class KeepingDTO {

    private long id;
    private Date startdate;
    private Date enddate;
    private long peopleId;
    private long vehicleId;
//    private PeopleEntity peopleByPeopleId;
//    private VehiclesEntity vehiclesByVehiclesId;
    //private Collection<OperationCostsEntity> operationCostsById;


    public KeepingDTO(long id, Date startdate, Date enddate, PeopleEntity peopleEntity, VehiclesEntity vehiclesEntity) {
        this.id = id;
        this.startdate = startdate;
        this.enddate = enddate;
        this.peopleId = peopleEntity.getId();
        this.vehicleId = vehiclesEntity.getId();
    }

    public KeepingDTO(KeepingEntity keepingEntity) {
        this.id = keepingEntity.getId();
        this.startdate = keepingEntity.getStartdate();
        this.enddate = keepingEntity.getEnddate();
        this.peopleId = keepingEntity.getPeopleId();
        this.vehicleId = keepingEntity.getVehiclesId();
    }
}
