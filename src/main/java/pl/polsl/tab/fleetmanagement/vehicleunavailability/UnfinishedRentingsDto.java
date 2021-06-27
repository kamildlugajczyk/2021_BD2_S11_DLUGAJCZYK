package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class UnfinishedRentingsDto {

    VehicleDTO vehicleDTO;
    long rentingId;
    Date startDate;
    Date predictedEndDate;



}
