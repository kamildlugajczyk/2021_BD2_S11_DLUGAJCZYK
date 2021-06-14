package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.exceptions.NotUniqueException;

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
                .orElseThrow(() -> new IdNotFoundException("Vehicle renting", id));

        return new VehicleRentingDto(vehicleRentingEntity);
    }

    public VehicleRentingDto getVehicleRentingByVehicleUnavailability(Long id) {

        VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findByVehicleUnavailabilityId(id)
                .orElseThrow(() -> new IdNotFoundException("Vehicle renting", id));

        return new VehicleRentingDto(vehicleRentingEntity);
    }

    public VehicleRentingDto addVehicleRenting(VehicleRentingDto vehicleRentingDTO) {

        try {
            VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.save(new VehicleRentingEntity(
                    vehicleRentingDTO.getStartmileage(),
                    vehicleRentingDTO.getEndmileage(),
                    vehicleRentingDTO.getIsbusiness(),
                    vehicleRentingDTO.getVehicleUnavailability()));

            return new VehicleRentingDto(vehicleRentingEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO add unique annotation in database script
                    throw new NotUniqueException("Operation cost", "id", vehicleRentingDTO.getId().toString());
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
            vehicleRentingEntity.get().setIsbusiness(vehicleRentingDTO.getIsbusiness());
            vehicleRentingEntity.get().setVehicleUnavailabilityId(vehicleRentingDTO.getVehicleUnavailability());
            return new VehicleRentingDto(vehicleRentingRepository.save(vehicleRentingEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO add unique annotation in database script
                    throw new NotUniqueException("Operation cost", "id", vehicleRentingDTO.getId().toString());
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteVehicleRenting(Long id) {

        try {
            this.vehicleRentingRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new IdNotFoundException("Vehicle", id);
        }

    }

}