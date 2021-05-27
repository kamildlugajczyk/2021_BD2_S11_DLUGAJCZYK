package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleRentingService {

    private final VehicleRentingRepository vehicleRentingRepository;

    public List<VehicleRentingDto> getVehicleRentings() {

        List<VehicleRentingDto> vehicleRentingDtos = new ArrayList<>();
        List<VehicleRentingEntity> vehicleRentingsEntities = new ArrayList<>(vehicleRentingRepository.findAll());

        vehicleRentingsEntities.addAll(vehicleRentingRepository.findAll());

        for (VehicleRentingEntity vehicleRentingEntity : vehicleRentingsEntities) {
            vehicleRentingDtos.add(new VehicleRentingDto(vehicleRentingEntity));
        }
        return vehicleRentingDtos;
    }

    public VehicleRentingDto getVehicleRenting(Long id) {

        VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle renting of id " + id + " not found"));

        return new VehicleRentingDto(vehicleRentingEntity);
    }

    // TODO: 19.05.2021 nie wiem jak to robić z tym VehicleUnavailability
    public VehicleRentingDto addVehicleRenting(VehicleRentingDto vehicleRentingDTO) {

        try {
            VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.save(new VehicleRentingEntity(
                    vehicleRentingDTO.getStartmileage(),
                    vehicleRentingDTO.getEndmileage(),
                    vehicleRentingDTO.getStartdate(),
                    vehicleRentingDTO.getEnddate(),
                    vehicleRentingDTO.getIsbusiness(),
                    vehicleRentingDTO.getVehicleUnavailability()));

            return new VehicleRentingDto(vehicleRentingEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO add unique annotation in database script
                    throw new ItemExistsInDatabaseException("Operation cost ( " + vehicleRentingDTO.getId() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public VehicleRentingDto updateVehicleRenting(Long id, VehicleRentingDto vehicleRentingDTO) {
        Optional<VehicleRentingEntity> vehicleRentingEntity = vehicleRentingRepository.findById(id);

        try {
            vehicleRentingEntity.get().setStartmileage(vehicleRentingDTO.getStartmileage());
            vehicleRentingEntity.get().setEndmileage(vehicleRentingDTO.getEndmileage());
            vehicleRentingEntity.get().setStartdate(vehicleRentingDTO.getStartdate());
            vehicleRentingEntity.get().setEnddate(vehicleRentingDTO.getEnddate());
            vehicleRentingEntity.get().setIsbusiness(vehicleRentingDTO.getIsbusiness());
            vehicleRentingEntity.get().setVehicleUnavailabilityId(vehicleRentingDTO.getVehicleUnavailability());
            return new VehicleRentingDto(vehicleRentingRepository.save(vehicleRentingEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO add unique annotation in database script
                    throw new ItemExistsInDatabaseException("Operation cost ( " + vehicleRentingDTO.getId() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteVehicleRenting(Long id) {

        try {
            this.vehicleRentingRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }

}
