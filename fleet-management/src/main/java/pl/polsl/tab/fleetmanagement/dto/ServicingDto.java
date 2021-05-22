package pl.polsl.tab.fleetmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

public class ServicingDto {
    @Getter @Setter private Long id;
    @Getter @Setter private BigDecimal price;
    @Getter @Setter private String description;
    @Getter @Setter private Date startDate;
    @Getter @Setter private Date endDate;
    @Getter @Setter private Long subcontractorsId;
    @Getter @Setter private Long serviceTypesId;
}
