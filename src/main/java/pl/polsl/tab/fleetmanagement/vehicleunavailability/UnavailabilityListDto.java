package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class UnavailabilityListDto {
    private final Long unavailabilityId;
    private final Long vehicleId;
    private final Long rentingId;
    private final Long servicingId;
    private final String brand;
    private final String model;
    private Date startDate;
    private Date endPredictDate;
    private Date endDate;
}

