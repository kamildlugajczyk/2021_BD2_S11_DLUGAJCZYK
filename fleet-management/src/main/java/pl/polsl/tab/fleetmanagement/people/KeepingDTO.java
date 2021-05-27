package pl.polsl.tab.fleetmanagement.people;

import lombok.Getter;
import pl.polsl.tab.fleetmanagement.exploitation.OperationCostEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import java.sql.Date;
import java.util.Collection;

@Getter
public class KeepingDTO {

    private long id;
    private Date startdate;
    private Date enddate;
    private int peopleId;
    private int vehiclesId;
    private PeopleEntity peopleByPeopleId;
    private VehiclesEntity vehiclesByVehiclesId;
    private Collection<OperationCostEntity> operationCostsById;

}
