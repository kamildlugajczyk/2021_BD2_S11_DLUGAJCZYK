package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityEntity;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class VehicleRentingWithUnavailabilityDto {

    private int startmileage;
    private int endmileage;
    private boolean isbusiness;
    private Date startDate;
    private Date predictEndDate;
    private Date endDate;
    private Long vehiclesId;
    private Long peopleId;

    public VehicleRentingWithUnavailabilityDto() {
    }

    public VehicleRentingWithUnavailabilityDto(VehicleUnavailabilityEntity vehicleUnavailabilityEntity,
                                               VehicleRentingEntity vehicleRentingEntity) {

        this.startmileage = vehicleRentingEntity.getStartmileage();
        this.endmileage = vehicleRentingEntity.getEndmileage();
        this.isbusiness = vehicleRentingEntity.getIsbusiness();
        this.startDate = vehicleUnavailabilityEntity.getStartDate();
        this.predictEndDate = vehicleUnavailabilityEntity.getPredictEndDate();
        this.endDate = vehicleUnavailabilityEntity.getEndDate();
        this.vehiclesId = vehicleUnavailabilityEntity.getVehiclesId();
        this.peopleId = vehicleUnavailabilityEntity.getPeopleId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleRentingWithUnavailabilityDto that = (VehicleRentingWithUnavailabilityDto) o;
        return startmileage == that.startmileage && endmileage == that.endmileage && isbusiness == that.isbusiness && Objects.equals(startDate, that.startDate) && Objects.equals(predictEndDate, that.predictEndDate) && Objects.equals(endDate, that.endDate) && Objects.equals(vehiclesId, that.vehiclesId) && Objects.equals(peopleId, that.peopleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startmileage, endmileage, isbusiness, startDate, predictEndDate, endDate, vehiclesId, peopleId);
    }
}
