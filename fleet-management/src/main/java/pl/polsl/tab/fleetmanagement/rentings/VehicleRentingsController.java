package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class VehicleRentingsController {

    private final VehicleRentingsService vehicleRentingsService;

    @GetMapping("/rentings")
    public List<VehicleRentingsDTO> getAllVehicleRentings() {
        return vehicleRentingsService.getAllVehicleRentings();
    }
}
