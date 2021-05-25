package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleRentingsService {

    private final VehicleRentingsRepository vehicleRentingsRepository;

    public List<VehicleRentingsDTO> getVehicleRentings() {

        List<VehicleRentingsDTO> vehicleRentingsDTOs = new ArrayList<>();

        List<VehicleRentingsEntity> vehicleRentingsEntities = new ArrayList<>(vehicleRentingsRepository.findAll());

        for (VehicleRentingsEntity vehicleRentingsEntity : vehicleRentingsEntities) {
            vehicleRentingsDTOs.add(new VehicleRentingsDTO(vehicleRentingsEntity.getId(),
                    vehicleRentingsEntity.getStartmileage(), vehicleRentingsEntity.getEndmileage(),
                    vehicleRentingsEntity.getStartdate(), vehicleRentingsEntity.getEnddate(),
                    vehicleRentingsEntity.getIsbusiness(), vehicleRentingsEntity.getVehicleUnavailabilityId()));
        }

        return vehicleRentingsDTOs;
    }

    public VehicleRentingsDTO getVehicleRenting(Long id) throws Exception{

       VehicleRentingsEntity entity = vehicleRentingsRepository.findById(id)
                .orElseThrow(() -> new Exception("Vehicle Renting " + id + " not found"));

        return new VehicleRentingsDTO(entity.getId(),
                entity.getStartmileage(), entity.getEndmileage(),
                entity.getStartdate(), entity.getEnddate(),
                entity.getIsbusiness(), entity.getVehicleUnavailabilityId());
    }

    // TODO: 19.05.2021 nie wiem jak to robić z tym VehicleUnavailability
    public VehicleRentingsEntity addVehicleRenting(VehicleRentingsDTO vehicleRentingsDTO) {

        VehicleRentingsEntity vehicleRentingsEntity = new VehicleRentingsEntity(vehicleRentingsDTO.getStartmileage(),
                vehicleRentingsDTO.getEndmileage(), vehicleRentingsDTO.getStartdate(), vehicleRentingsDTO.getEnddate(),
                vehicleRentingsDTO.getIsbusiness(), vehicleRentingsDTO.getVehicleUnavailability());

        return vehicleRentingsRepository.save(vehicleRentingsEntity);
    }

    @Transactional
    public Optional<VehicleRentingsDTO> updateVehicleRenting(Long id, VehicleRentingsDTO vehicleRentingsDTO) {

        Optional<VehicleRentingsEntity> vehicleRentingsEntity = vehicleRentingsRepository.findById(id);

        if (vehicleRentingsEntity.isPresent()) {

            if (vehicleRentingsDTO.getStartmileage() != vehicleRentingsEntity.get().getStartmileage()) {
                vehicleRentingsEntity.get().setStartmileage(vehicleRentingsDTO.getStartmileage());
            }

            if (vehicleRentingsDTO.getEndmileage() != vehicleRentingsEntity.get().getEndmileage()) {
                vehicleRentingsEntity.get().setEndmileage(vehicleRentingsDTO.getEndmileage());
            }

            if (vehicleRentingsDTO.getStartdate() != null &&
                    !Objects.equals(vehicleRentingsEntity.get().getStartdate(),
                            vehicleRentingsDTO.getStartdate())) {
                vehicleRentingsEntity.get().setStartdate(vehicleRentingsDTO.getStartdate());
            }

            if (vehicleRentingsDTO.getEnddate() != null &&
                    !Objects.equals(vehicleRentingsEntity.get().getEnddate(),
                            vehicleRentingsDTO.getEnddate())) {
                vehicleRentingsEntity.get().setStartdate(vehicleRentingsDTO.getEnddate());
            }

            if (vehicleRentingsDTO.getIsbusiness() != null && vehicleRentingsDTO.getIsbusiness().length() > 0 &&
                    !Objects.equals(vehicleRentingsEntity.get().getIsbusiness(), vehicleRentingsDTO.getIsbusiness())) {
                vehicleRentingsEntity.get().setIsbusiness((vehicleRentingsDTO.getIsbusiness()));
            }

            //// TODO: 19.05.2021 nie wiem jak to będzie sprawdzane itede
            if (vehicleRentingsDTO.getVehicleUnavailability() != vehicleRentingsEntity.get().getVehicleUnavailabilityId()) {
                vehicleRentingsEntity.get().setVehicleUnavailabilityId(vehicleRentingsDTO.getVehicleUnavailability());
            }

            vehicleRentingsRepository.save(vehicleRentingsEntity.get());
            return Optional.of(new VehicleRentingsDTO(
                    vehicleRentingsEntity.get().getStartmileage(),
                    vehicleRentingsEntity.get().getEndmileage(),
                    vehicleRentingsEntity.get().getStartdate(),
                    vehicleRentingsEntity.get().getEnddate(),
                    vehicleRentingsEntity.get().getIsbusiness(),
                    vehicleRentingsEntity.get().getVehicleUnavailabilityId()));
        }

        return Optional.empty();
    }

    public void deleteVehicleRenting(Long id) throws Exception {

        try {
            this.vehicleRentingsRepository.deleteById(id);
        } catch (RuntimeException ignored) {
            throw new Exception("Vehicle renting " + id + " not found");
        }

    }

}
