package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.rentings.VehicleRentingEntity;
import pl.polsl.tab.fleetmanagement.rentings.VehicleRentingService;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;
import pl.polsl.tab.fleetmanagement.servicing.ServicingRepository;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class VehicleUnavailabilityService {

    private final VehicleUnavailabilityRepository vehicleUnavailabilityRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;

    @Autowired
    public VehicleUnavailabilityService(VehicleUnavailabilityRepository vehicleUnavailabilityRepository,
                                        ModelMapper modelMapper,
                                        VehicleRentingService vehicleRentingService,
                                        ServicingRepository servicingRepository,
                                        VehicleService vehicleService) {
        this.vehicleUnavailabilityRepository = vehicleUnavailabilityRepository;
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
    }

    public List<VehicleUnavailabilityEntity> getVehicleUnavailabilityEntities(){
        return this.vehicleUnavailabilityRepository.findAll();
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
//        LocalDate now = LocalDate.now();
//        Date nowSql = java.sql.Date.valueOf(String.valueOf(now));
        Date now = new Date();
        VehicleUnavailabilityEntity vue = this.getVehicleUnavailabilityById(id);
        vue.setEndDate(now);
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

    public List<UnavailabilityListDto> getUnavailabilityList() {
        List<VehicleUnavailabilityEntity> vehicleUnavailabilityEntities =
                this.getVehicleUnavailabilityEntities();
        List<UnavailabilityListDto> unavailabilityListDtos = new ArrayList<>();


        for(VehicleUnavailabilityEntity vehicleUnavailabilityEntity : vehicleUnavailabilityEntities){
            Long servicingId = null;
            Long rentingId = null;
            Optional<VehicleRentingEntity> vehicleRentingEntity = vehicleUnavailabilityEntity
                    .getVehicleRentingsById().stream().findFirst();

            Optional<ServicingEntity> servicingEntity = vehicleUnavailabilityEntity
                    .getServicings().stream().findFirst();

            if(vehicleRentingEntity.isPresent())
                rentingId = vehicleRentingEntity.get().getId();

            if(servicingEntity.isPresent())
                servicingId = servicingEntity.get().getId();


            unavailabilityListDtos.add(new UnavailabilityListDto(
                    vehicleUnavailabilityEntity.getId(),
                    vehicleUnavailabilityEntity.getVehiclesId(),
                    rentingId,
                    servicingId,
                    vehicleService.getVehicle(vehicleUnavailabilityEntity
                            .getVehiclesId()).getBrandmodel().getBrand(),
                    vehicleService.getVehicle(vehicleUnavailabilityEntity
                            .getVehiclesId()).getBrandmodel().getModel(),
                    vehicleUnavailabilityEntity.getStartDate(),
                    vehicleUnavailabilityEntity.getEndDate()
            ));

        }
        return unavailabilityListDtos;
    }
}
