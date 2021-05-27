package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/rentings")
public class VehicleRentingController {

    private final VehicleRentingService vehicleRentingService;

    @GetMapping
    public List<VehicleRentingDTO> getVehicleRantings() {
        return vehicleRentingService.getVehicleRentings();
    }

    @GetMapping(path = "/{id}")
    public VehicleRentingDTO getVehicleRenting(@PathVariable Long id) {

        try {
            return this.vehicleRentingService.getVehicleRenting(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<VehicleRentingDTO> addVehicleRenting(@RequestBody VehicleRentingDTO vehicleRentingDTO) {
        try {
            VehicleRentingEntity response = vehicleRentingService.addVehicleRenting(vehicleRentingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicleRentingDTO);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);

            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                }
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteVehicleRenting(@PathVariable Long id) {
        try {
            this.vehicleRentingService.deleteVehicleRenting(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<VehicleRentingDTO> updateVehicleRenting(@PathVariable Long id, @RequestBody VehicleRentingDTO vehicleRentingDTO) {
        Optional<VehicleRentingDTO> response = vehicleRentingService.updateVehicleRenting(id, vehicleRentingDTO);

        return response
                .map(purposesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }


}
