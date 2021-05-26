package pl.polsl.tab.fleetmanagement.keeping;

import lombok.Getter;
import pl.polsl.tab.fleetmanagement.models.OperationCostsEntity;
import pl.polsl.tab.fleetmanagement.models.VehiclesEntity;
import pl.polsl.tab.fleetmanagement.people.PersonEntity;

import java.sql.Date;
import java.util.Collection;

@Getter
public class KeepingDTO {

    private long id;
    private Date startdate;
    private Date enddate;
    private long peopleId;
    private long vehicleId;
    private PersonEntity peopleByPeopleId;
    private VehiclesEntity vehiclesByVehiclesId;
    private Collection<OperationCostsEntity> operationCostsById;


    public KeepingDTO(long id, Date startdate, Date enddate, PersonEntity personEntity, VehiclesEntity vehicleEntity) {
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
