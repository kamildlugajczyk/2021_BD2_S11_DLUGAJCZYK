package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleRentingsService {

    private final VehicleRentingsRepository vehicleRentingsRepository;

    public List<VehicleRentingsDTO> getAllVehicleRentings() {

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

    public VehicleRentingsDTO getVehicleRentingById(Long vehicleRentingId) {

        Optional<VehicleRentingsEntity> vehicleRentingsEntityOptional = vehicleRentingsRepository.findById(vehicleRentingId);

        if (vehicleRentingsEntityOptional.isEmpty()) {
            throw new IllegalStateException("vehicle renting with id " + vehicleRentingId + " does not exists");
        }

        return new VehicleRentingsDTO(vehicleRentingsEntityOptional.get().getId(),
                vehicleRentingsEntityOptional.get().getStartmileage(), vehicleRentingsEntityOptional.get().getEndmileage(),
                vehicleRentingsEntityOptional.get().getStartdate(), vehicleRentingsEntityOptional.get().getEnddate(),
                vehicleRentingsEntityOptional.get().getIsbusiness(), vehicleRentingsEntityOptional.get().getVehicleUnavailabilityId());
    }

    public void deleteVehicleRenting(Long vehicleRentingId) {

        boolean exists = vehicleRentingsRepository.existsById(vehicleRentingId);

        if (!exists) {
            throw new IllegalStateException("vehicle renting with id " + vehicleRentingId + " does not exists");
        }
        vehicleRentingsRepository.deleteById(vehicleRentingId);
    }

    // TODO: 19.05.2021 nie wiem jak to robić z tym VehicleUnavailability
    public void addNewVehicleRenting(VehicleRentingsDTO vehicleRentingsDTO) {

        VehicleRentingsEntity vehicleRentingsEntity = new VehicleRentingsEntity(vehicleRentingsDTO.getStartmileage(),
                vehicleRentingsDTO.getEndmileage(), vehicleRentingsDTO.getStartdate(), vehicleRentingsDTO.getEnddate(),
                vehicleRentingsDTO.getIsbusiness(), vehicleRentingsDTO.getVehicleUnavailability());

        vehicleRentingsRepository.save(vehicleRentingsEntity);
    }

    public void updateVehicleRenting(Long vehicleRentingId, int startmileage, int endmileage,
                                     Date startdate, Date enddate, String isbusiness, int vehicleUnavailabilityId) {

        VehicleRentingsEntity vehicleRentingsEntity = vehicleRentingsRepository.findById(vehicleRentingId)
                .orElseThrow(() -> new IllegalStateException("vehicle renting with id " + vehicleRentingId + " does not exists"));

        if (startmileage != vehicleRentingsEntity.getStartmileage()) {
            vehicleRentingsEntity.setStartmileage(startmileage);
        }

        if (endmileage != vehicleRentingsEntity.getEndmileage()) {
            vehicleRentingsEntity.setEndmileage(endmileage);
        }

        if (startdate != null && !Objects.equals(vehicleRentingsEntity.getStartdate(), startdate)) {
            vehicleRentingsEntity.setStartdate(startdate);
        }

        if (enddate != null && !Objects.equals(vehicleRentingsEntity.getEnddate(), enddate)) {
            vehicleRentingsEntity.setStartdate(enddate);
        }

        if (isbusiness != null && isbusiness.length() > 0 && !Objects.equals(vehicleRentingsEntity.getIsbusiness(), isbusiness)) {
            vehicleRentingsEntity.setIsbusiness((isbusiness));
        }

        //// TODO: 19.05.2021 nie wiem jak to będzie sprawdzane itede
        if (vehicleUnavailabilityId != vehicleRentingsEntity.getVehicleUnavailabilityId()) {
            vehicleRentingsEntity.setVehicleUnavailabilityId(vehicleUnavailabilityId);
        }
    }

}
