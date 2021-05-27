package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class VehicleRentingDTO {

    private long id;
    private int startmileage;
    private int endmileage;
    private Date startdate;
    private Date enddate;
    private String isbusiness;
    private int vehicleUnavailability;

    public VehicleRentingDTO(int startmileage, int endmileage, Date startdate, Date enddate,
                             String isbusiness, int vehicleUnavailability) {

        this.startmileage = startmileage;
        this.endmileage = endmileage;
        this.startdate = startdate;
        this.enddate = enddate;
        this.isbusiness = isbusiness;
        this.vehicleUnavailability = vehicleUnavailability;
    }

    public VehicleRentingDTO() {
    }

    public VehicleRentingDTO(VehicleRentingEntity vehicleRentingEntity) {
        this.startmileage = vehicleRentingEntity.getStartmileage();
        this.endmileage = vehicleRentingEntity.getEndmileage();
        this.startdate = vehicleRentingEntity.getStartdate();
        this.enddate = vehicleRentingEntity.getEnddate();
        this.isbusiness = vehicleRentingEntity.getIsbusiness();
        this.vehicleUnavailability = vehicleRentingEntity.getVehicleUnavailabilityId();
    }
}
