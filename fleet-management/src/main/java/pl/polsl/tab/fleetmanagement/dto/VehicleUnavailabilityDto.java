package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@NoArgsConstructor
public class VehicleUnavailabilityDto {
    @Getter @Setter private Long id;
    @Getter @Setter private Date startDate;
    @Getter @Setter private Date endDate;
    @Getter @Setter private String business;
    @Getter @Setter private Long vehiclesId;
    @Getter @Setter private Long peopleId;

    public VehicleUnavailabilityDto(Date startDate, Date endDate, String business, Long vehiclesId, Long peopleId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.business = business;
        this.vehiclesId = vehiclesId;
        this.peopleId = peopleId;
    }
}
