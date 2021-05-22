package pl.polsl.tab.fleetmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.dto.VehicleUnavailabilityDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.VehicleUnavailabilityEntity;
import pl.polsl.tab.fleetmanagement.repositories.VehicleUnavailabilityRepository;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class VehicleUnavailabilityService {

    private final VehicleUnavailabilityRepository vehicleUnavailabilityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleUnavailabilityService(VehicleUnavailabilityRepository vehicleUnavailabilityRepository, ModelMapper modelMapper) {
        this.vehicleUnavailabilityRepository = vehicleUnavailabilityRepository;
        this.modelMapper = modelMapper;
    }

    public VehicleUnavailabilityEntity getVehicleUnavailabilityById(Long id) {
        return this.vehicleUnavailabilityRepository
            .findById(id)
            .orElseThrow(() -> new IdNotFoundInDatabaseException("Unavailability " + id + " not found"));
    }

    public Long addVehicleUnavailability(VehicleUnavailabilityDto dto, boolean archive) {

        if(dto.getEndDate().before(dto.getStartDate()))
            throw new RuntimeException("Start Date is after Predict End Day");

        VehicleUnavailabilityEntity vue = this.modelMapper.map(dto, VehicleUnavailabilityEntity.class);

        if (!archive) {
            vue.setPredictEndDate(dto.getEndDate());
            vue.setEndDate(null);
        } else {
            vue.setPredictEndDate(null);
            vue.setEndDate(dto.getEndDate());
        }

        vue = this.vehicleUnavailabilityRepository.save(vue);
        return vue.getId();
    }

    public void unlockVehicleBySetEndDate(Long id) {
        LocalDate now = LocalDate.now();
        Date nowSql = java.sql.Date.valueOf(String.valueOf(now));
        VehicleUnavailabilityEntity vue = this.getVehicleUnavailabilityById(id);
        vue.setEndDate(nowSql);
        this.vehicleUnavailabilityRepository.save(vue);
    }
}
