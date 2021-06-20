package pl.polsl.tab.fleetmanagement.rentings;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/rentings")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class VehicleRentingController {

    private final VehicleRentingService vehicleRentingService;

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<VehicleRentingDto> getVehicleRantings() {
        return vehicleRentingService.getVehicleRentings();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public VehicleRentingDto getVehicleRenting(@PathVariable Long id) {
        try {
            return this.vehicleRentingService.getVehicleRenting(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

//    @PostMapping
//    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
//    public VehicleRentingDto addVehicleRenting(@RequestBody VehicleRentingDto vehicleRentingDTO) {
//        try {
//            return vehicleRentingService.addVehicleRenting(vehicleRentingDTO);
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
//        }
//    }

    @PostMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public VehicleRentingWithUnavailabilityDto addVehicleRenting(
            @RequestBody VehicleRentingWithUnavailabilityDto vehicleRentingWithUnavailabilityDto) {
        try {
            return vehicleRentingService.addVehicleRentingWithUnavailability(vehicleRentingWithUnavailabilityDto);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteVehicleRenting(@PathVariable Long id) {
        vehicleRentingService.deleteVehicleRenting(id);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public VehicleRentingDto updateVehicleRenting(@PathVariable Long id, @RequestBody VehicleRentingDto vehicleRentingDTO) {
        try {
            return vehicleRentingService.updateVehicleRenting(id, vehicleRentingDTO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


}
