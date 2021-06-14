package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleUnavailabilityController {

    private final VehicleUnavailabilityService vehicleUnavailabilityService;

    @Autowired
    public VehicleUnavailabilityController(VehicleUnavailabilityService vehicleUnavailabilityService) {
        this.vehicleUnavailabilityService = vehicleUnavailabilityService;
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<UnavailabilityListDto> getUnavailabilityList() {
        return this.vehicleUnavailabilityService.getUnavailabilityList();

    }
}
