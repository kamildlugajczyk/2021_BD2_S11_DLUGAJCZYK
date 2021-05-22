package pl.polsl.tab.fleetmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.dto.VehicleUnavailabilityDto;
import pl.polsl.tab.fleetmanagement.models.VehicleUnavailabilityEntity;
import pl.polsl.tab.fleetmanagement.repositories.VehicleUnavailabilityRepository;

@Component
public class VehicleUnavailabilityService {

    private final VehicleUnavailabilityRepository vehicleUnavailabilityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleUnavailabilityService(VehicleUnavailabilityRepository vehicleUnavailabilityRepository, ModelMapper modelMapper) {
        this.vehicleUnavailabilityRepository = vehicleUnavailabilityRepository;
        this.modelMapper = modelMapper;
    }

    public Long addVehicleUnavailability(VehicleUnavailabilityDto dto) {
        // TODO validate
        VehicleUnavailabilityEntity vue = this.modelMapper.map(dto, VehicleUnavailabilityEntity.class);
        vue = this.vehicleUnavailabilityRepository.save(vue);
        return vue.getId();
    }
}
