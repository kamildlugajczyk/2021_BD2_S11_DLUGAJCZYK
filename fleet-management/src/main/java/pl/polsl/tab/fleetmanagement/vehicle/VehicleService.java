package pl.polsl.tab.fleetmanagement.vehicle;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<VehicleDTO> getAllVehicles() {
        List<VehiclesEntity> vehiclesEntities = new ArrayList<>();
        List<VehicleDTO> vehiclesDTOs = new ArrayList<>();

        vehicleRepository.findAll().forEach(vehiclesEntities::add);

        for (VehiclesEntity vehiclesEntity : vehiclesEntities) {
            vehiclesDTOs.add(new VehicleDTO(vehiclesEntity.getId(), vehiclesEntity.getVin(), vehiclesEntity.getEquipmentlevel(),
                    vehiclesEntity.getMileage(), vehiclesEntity.getAvgfuelconsumption(), vehiclesEntity.getTypesByTypesId(),
                    vehiclesEntity.getPurposesByPurposesId(), vehiclesEntity.getBrandsModelsByBrandsModelsId()));
        }

        return vehiclesDTOs;
    }


}
