package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            .orElseThrow(() -> new IdNotFoundException("Unavailability", id));
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

    public void deleteVehicleUnavailability(Long id) {
        this.vehicleUnavailabilityRepository.deleteById(id);
    }

    public List<VehicleUnavailabilityDto> getVehicleUnavailabilityByPersonId(Long personId) {

        List<VehicleUnavailabilityDto> vehicleUnavailabilityDtos = new ArrayList<>();

        List<VehicleUnavailabilityEntity> vehicleUnavailabilityEntities = new ArrayList<>(vehicleUnavailabilityRepository.findAllByPeopleId(personId));

        for (VehicleUnavailabilityEntity vehicleUnavailabilityEntity : vehicleUnavailabilityEntities) {
            vehicleUnavailabilityDtos.add(new VehicleUnavailabilityDto(
                    vehicleUnavailabilityEntity.getStartDate(),
                    vehicleUnavailabilityEntity.getEndDate(),
                    vehicleUnavailabilityEntity.getVehiclesId(),
                    vehicleUnavailabilityEntity.getPeopleId(),
                    vehicleUnavailabilityEntity.getId()));
        }
        return vehicleUnavailabilityDtos;

    }
}
