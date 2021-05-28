package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/rentings")
public class VehicleRentingController {

    private final VehicleRentingService vehicleRentingService;

    @GetMapping
    public List<VehicleRentingDto> getVehicleRantings() {
        return vehicleRentingService.getVehicleRentings();
    }

    @GetMapping(path = "/{id}")
    public VehicleRentingDto getVehicleRenting(@PathVariable Long id) {
        try {
            return this.vehicleRentingService.getVehicleRenting(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    public VehicleRentingDto addVehicleRenting(@RequestBody VehicleRentingDto vehicleRentingDTO) {
        try {
            return vehicleRentingService.addVehicleRenting(vehicleRentingDTO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteVehicleRenting(@PathVariable Long id) {
        vehicleRentingService.deleteVehicleRenting(id);
    }

    @PutMapping(path = "/{id}")
    public VehicleRentingDto updateVehicleRenting(@PathVariable Long id, @RequestBody VehicleRentingDto vehicleRentingDTO) {
        try {
            return vehicleRentingService.updateVehicleRenting(id, vehicleRentingDTO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


}
