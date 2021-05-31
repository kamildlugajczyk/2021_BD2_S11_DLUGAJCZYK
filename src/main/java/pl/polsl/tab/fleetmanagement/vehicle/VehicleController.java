package pl.polsl.tab.fleetmanagement.vehicle;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/vehicles")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
}
