package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RentVehicleDto {

    private Date startDate;
    private Date predictEndDate;
    private boolean isBusiness;
}
