package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class ServiceRequestDto {
    @Getter private Long id;
    @Getter @Setter private Date date;
    @Getter @Setter private String description;
    @Getter @Setter private Long serviceTypesId;
    @Getter @Setter private Long vehiclesId;
    @Getter @Setter private Long peopleId;
    @Getter @Setter private Boolean processed;
}
