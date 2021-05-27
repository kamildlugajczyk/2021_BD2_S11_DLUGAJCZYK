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
public class VehicleRentingService {

    private final VehicleRentingRepository vehicleRentingRepository;

    public List<VehicleRentingDTO> getVehicleRentings() {

        List<VehicleRentingDTO> vehicleRentingDTOS = new ArrayList<>();

        List<VehicleRentingEntity> vehicleRentingsEntities = new ArrayList<>(vehicleRentingRepository.findAll());

        for (VehicleRentingEntity vehicleRentingEntity : vehicleRentingsEntities) {
            vehicleRentingDTOS.add(new VehicleRentingDTO(vehicleRentingEntity.getId(),
                    vehicleRentingEntity.getStartmileage(), vehicleRentingEntity.getEndmileage(),
                    vehicleRentingEntity.getStartdate(), vehicleRentingEntity.getEnddate(),
                    vehicleRentingEntity.getIsbusiness(), vehicleRentingEntity.getVehicleUnavailabilityId()));
        }

        return vehicleRentingDTOS;
    }

    public VehicleRentingDTO getVehicleRenting(Long id) throws Exception{

       VehicleRentingEntity entity = vehicleRentingRepository.findById(id)
                .orElseThrow(() -> new Exception("Vehicle Renting " + id + " not found"));

        return new VehicleRentingDTO(entity.getId(),
                entity.getStartmileage(), entity.getEndmileage(),
                entity.getStartdate(), entity.getEnddate(),
                entity.getIsbusiness(), entity.getVehicleUnavailabilityId());
    }

    // TODO: 19.05.2021 nie wiem jak to robić z tym VehicleUnavailability
    public VehicleRentingEntity addVehicleRenting(VehicleRentingDTO vehicleRentingDTO) {

        VehicleRentingEntity vehicleRentingEntity = new VehicleRentingEntity(vehicleRentingDTO.getStartmileage(),
                vehicleRentingDTO.getEndmileage(), vehicleRentingDTO.getStartdate(), vehicleRentingDTO.getEnddate(),
                vehicleRentingDTO.getIsbusiness(), vehicleRentingDTO.getVehicleUnavailability());

        return vehicleRentingRepository.save(vehicleRentingEntity);
    }

    @Transactional
    public Optional<VehicleRentingDTO> updateVehicleRenting(Long id, VehicleRentingDTO vehicleRentingDTO) {

        Optional<VehicleRentingEntity> vehicleRentingsEntity = vehicleRentingRepository.findById(id);

        if (vehicleRentingsEntity.isPresent()) {

            if (vehicleRentingDTO.getStartmileage() != vehicleRentingsEntity.get().getStartmileage()) {
                vehicleRentingsEntity.get().setStartmileage(vehicleRentingDTO.getStartmileage());
            }

            if (vehicleRentingDTO.getEndmileage() != vehicleRentingsEntity.get().getEndmileage()) {
                vehicleRentingsEntity.get().setEndmileage(vehicleRentingDTO.getEndmileage());
            }

            if (vehicleRentingDTO.getStartdate() != null &&
                    !Objects.equals(vehicleRentingsEntity.get().getStartdate(),
                            vehicleRentingDTO.getStartdate())) {
                vehicleRentingsEntity.get().setStartdate(vehicleRentingDTO.getStartdate());
            }

            if (vehicleRentingDTO.getEnddate() != null &&
                    !Objects.equals(vehicleRentingsEntity.get().getEnddate(),
                            vehicleRentingDTO.getEnddate())) {
                vehicleRentingsEntity.get().setStartdate(vehicleRentingDTO.getEnddate());
            }

            if (vehicleRentingDTO.getIsbusiness() != null && vehicleRentingDTO.getIsbusiness().length() > 0 &&
                    !Objects.equals(vehicleRentingsEntity.get().getIsbusiness(), vehicleRentingDTO.getIsbusiness())) {
                vehicleRentingsEntity.get().setIsbusiness((vehicleRentingDTO.getIsbusiness()));
            }

            //// TODO: 19.05.2021 nie wiem jak to będzie sprawdzane itede
            if (vehicleRentingDTO.getVehicleUnavailability() != vehicleRentingsEntity.get().getVehicleUnavailabilityId()) {
                vehicleRentingsEntity.get().setVehicleUnavailabilityId(vehicleRentingDTO.getVehicleUnavailability());
            }

            vehicleRentingRepository.save(vehicleRentingsEntity.get());
            return Optional.of(new VehicleRentingDTO(
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
            this.vehicleRentingRepository.deleteById(id);
        } catch (RuntimeException ignored) {
            throw new Exception("Vehicle renting " + id + " not found");
        }

    }

}
