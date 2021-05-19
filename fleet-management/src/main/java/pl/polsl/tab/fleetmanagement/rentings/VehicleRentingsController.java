package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/rentings")
public class VehicleRentingsController {

    private final VehicleRentingsService vehicleRentingsService;

    @GetMapping
    public List<VehicleRentingsDTO> getAllVehicleRentings() {
        return vehicleRentingsService.getAllVehicleRentings();
    }

    @GetMapping(path = "/{vehicleRentingId}")
    public VehicleRentingsDTO getVehicleRentingById(Long vehicleRentingId) {
        return vehicleRentingsService.getVehicleRentingById(vehicleRentingId);
    }

    @DeleteMapping(path = "/{vehicleRentingId}")
    public void deleteVehicleRenting(@PathVariable("vehicleRentingId") Long vehicleRentingId) {
        vehicleRentingsService.deleteVehicleRenting(vehicleRentingId);
    }

    @PostMapping
    public void addNewVehicleRenting(@RequestBody VehicleRentingsDTO vehicleRentingsEntity) {
        vehicleRentingsService.addNewVehicleRenting(vehicleRentingsEntity);
    }

    @PutMapping(path = "/{vehicleRentingId}")
    public void updateVehicleRenting(
            @PathVariable("vehicleRentingId") Long vehicleRentingId,
            @RequestParam(required = false) int startmileage,
            @RequestParam(required = false) int endmileage,
            @RequestParam(required = false) Date startdate,
            @RequestParam(required = false) Date enddate,
            @RequestParam(required = false) String isbusiness,
            @RequestParam(required = false) int vehicleUnavailability) {

        vehicleRentingsService.updateVehicleRenting(vehicleRentingId, startmileage, endmileage,
                startdate, enddate, isbusiness, vehicleUnavailability);

    }


}
