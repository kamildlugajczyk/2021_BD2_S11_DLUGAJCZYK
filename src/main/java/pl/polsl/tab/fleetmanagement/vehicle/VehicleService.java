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
        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        List<VehicleDTO> vehicleDTOs = new ArrayList<>();

        vehicleRepository.findAll().forEach(vehicleEntities::add);

        for (VehicleEntity vehicleEntity : vehicleEntities) {
            vehicleDTOs.add(new VehicleDTO(vehicleEntity.getId(), vehicleEntity.getVin(), vehicleEntity.getEquipmentlevel(),
                    vehicleEntity.getMileage(), vehicleEntity.getAvgfuelconsumption(), vehicleEntity.getTypesByTypesId(),
                    vehicleEntity.getPurposesByPurposesId(), vehicleEntity.getBrandsModelsByBrandsModelsId()));
        }

        return vehicleDTOs;
    }
}
