package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.rentings.RentVehicleDto;

import java.util.List;

@RestController
@RequestMapping(path = "/unavailability")
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

    @GetMapping(path = "/{vehicleId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<UnavailabilityListDto> getUnavailabilityListByVehicleId(@PathVariable Long vehicleId) {
        return this.vehicleUnavailabilityService.getUnavailabilityListByVehicleId(vehicleId);
    }

    @GetMapping(path = "/unfinished")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<UnfinishedRentingsDto> getUnfinishedVehicleRentingsByUser() {
        return vehicleUnavailabilityService.getUnfinishedVehicleRentingsByUser();
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void cancelVehicleRenting(@PathVariable Long id) {
        vehicleUnavailabilityService.cancelVehicleRenting(id);
    }
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void finishVehicleRenting(@PathVariable Long id) {
        vehicleUnavailabilityService.finishVehicleRenting(id);
    }

    @PostMapping(path = "/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void rentVehicle(@PathVariable Long id, @RequestBody RentVehicleDto rentVehicleDto) {
        try {
            vehicleUnavailabilityService.rentVehicle(id, rentVehicleDto);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


}
