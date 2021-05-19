package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleRentingsService {
    
    private final VehicleRentingsRepository vehicleRentingsRepository;
    
    public List<VehicleRentingsDTO> getAllVehicleRentings(){

        List<VehicleRentingsDTO> vehicleRentingsDTOs = new ArrayList<>();

        List<VehicleRentingsEntity> vehicleRentingsEntities = new ArrayList<>(vehicleRentingsRepository.findAll());
        
        for(VehicleRentingsEntity vehicleRentingsEntity : vehicleRentingsEntities){
            vehicleRentingsDTOs.add(new VehicleRentingsDTO(vehicleRentingsEntity.getId(), 
                    vehicleRentingsEntity.getStartmileage(), vehicleRentingsEntity.getEndmileage(), 
                    vehicleRentingsEntity.getStartdate(), vehicleRentingsEntity.getEnddate(),
                    vehicleRentingsEntity.getIsbusiness(), vehicleRentingsEntity.getVehicleUnavailabilityByVehicleUnavailabilityId()));
        }

        return vehicleRentingsDTOs;
    }
    
}
