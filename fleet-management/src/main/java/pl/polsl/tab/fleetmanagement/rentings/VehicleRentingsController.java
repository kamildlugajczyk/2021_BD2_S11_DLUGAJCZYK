package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/rentings")
public class VehicleRentingsController {

    private final VehicleRentingsService vehicleRentingsService;

    @GetMapping
    public List<VehicleRentingsDTO> getVehicleRantings() {
        return vehicleRentingsService.getVehicleRentings();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<VehicleRentingsDTO> getVehicleRenting(@PathVariable Long id) {

        Optional<VehicleRentingsDTO> response = vehicleRentingsService.getVehicleRenting(id);

        return response
                .map(operationTypeEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<VehicleRentingsDTO> addVehicleRenting(@RequestBody VehicleRentingsDTO vehicleRentingsDTO) {
        try {
            VehicleRentingsEntity response = vehicleRentingsService.addVehicleRenting(vehicleRentingsDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicleRentingsDTO);
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
    public ResponseEntity<VehicleRentingsDTO> deleteVehicleRenting(@PathVariable Long id) {
        Optional<VehicleRentingsDTO> response = vehicleRentingsService.deleteVehicleRenting(id);

        return response
                .map(purposesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<VehicleRentingsDTO> updateVehicleRenting(@PathVariable Long id, @RequestBody VehicleRentingsDTO vehicleRentingsDTO) {
        Optional<VehicleRentingsDTO> response = vehicleRentingsService.updateVehicleRenting(id, vehicleRentingsDTO);

        return response
                .map(purposesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }


}
