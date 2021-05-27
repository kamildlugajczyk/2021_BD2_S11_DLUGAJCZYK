package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VehicleRentingDto {

    private long id;
    private int startmileage;
    private int endmileage;
    private boolean isbusiness;
    private int vehicleUnavailability;

    public VehicleRentingDto(int startmileage, int endmileage,
                             boolean isbusiness, int vehicleUnavailability) {

        this.startmileage = startmileage;
        this.endmileage = endmileage;
        this.isbusiness = isbusiness;
        this.vehicleUnavailability = vehicleUnavailability;
    }

    public VehicleRentingDto() {
    }

    public VehicleRentingDto(VehicleRentingEntity vehicleRentingEntity) {
        this.id = vehicleRentingEntity.getId();
        this.startmileage = vehicleRentingEntity.getStartmileage();
        this.endmileage = vehicleRentingEntity.getEndmileage();
        this.isbusiness = vehicleRentingEntity.getIsbusiness();
        this.vehicleUnavailability = vehicleRentingEntity.getVehicleUnavailabilityId();
    }

    public boolean getIsbusiness() {
        return isbusiness;

    }
}
