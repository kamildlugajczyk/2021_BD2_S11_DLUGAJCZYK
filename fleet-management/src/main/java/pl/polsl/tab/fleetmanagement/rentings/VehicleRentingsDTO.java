package pl.polsl.tab.fleetmanagement.rentings;

import lombok.Getter;

import java.sql.Date;

@Getter
public class VehicleRentingsDTO {

    private long id;
    private int startmileage;
    private int endmileage;
    private Date startdate;
    private Date enddate;
    private String isbusiness;
    private int vehicleUnavailability;

    public VehicleRentingsDTO(long id, int startmileage, int endmileage, Date startdate, Date enddate, String isbusiness, VehicleUnavailabilityEntity vehicleUnavailabilityId) {
        this.id = id;
        this.startmileage = startmileage;
        this.endmileage = endmileage;
        this.startdate = startdate;
        this.enddate = enddate;
        this.isbusiness = isbusiness;
        this.vehicleUnavailability = (int) vehicleUnavailabilityId.getId();
    }


}
