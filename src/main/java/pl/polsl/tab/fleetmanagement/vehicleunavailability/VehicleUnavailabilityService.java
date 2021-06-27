package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.auth.UserPrincipal;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;
import pl.polsl.tab.fleetmanagement.rentings.*;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;
import pl.polsl.tab.fleetmanagement.servicing.ServicingRepository;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleRepository;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class VehicleUnavailabilityService {

    private final VehicleUnavailabilityRepository vehicleUnavailabilityRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;
    private final PersonRepository personRepository;
    private final VehicleRentingRepository vehicleRentingRepository;
    private final VehicleRepository vehicleRepository;


    @Autowired
    public VehicleUnavailabilityService(VehicleUnavailabilityRepository vehicleUnavailabilityRepository,
                                        ModelMapper modelMapper,
                                        VehicleRentingService vehicleRentingService,
                                        ServicingRepository servicingRepository,
                                        VehicleService vehicleService, PersonRepository personRepository,
                                        VehicleRentingRepository vehicleRentingRepository,
                                        VehicleRepository vehicleRepository) {
        this.vehicleUnavailabilityRepository = vehicleUnavailabilityRepository;
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
        this.personRepository = personRepository;
        this.vehicleRentingRepository = vehicleRentingRepository;
        this.vehicleRepository = vehicleRepository;
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

        if(dto.getPredictEndDate().before(dto.getStartDate()))
            throw new RuntimeException("Start Date is after Predict End Day");

        VehicleUnavailabilityEntity vue = this.modelMapper.map(dto, VehicleUnavailabilityEntity.class);

        if (!archive) {
            vue.setPredictEndDate(dto.getPredictEndDate());
            vue.setEndDate(null);
        } else {
            vue.setPredictEndDate(null);
            vue.setEndDate(dto.getPredictEndDate());
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
                    vehicleUnavailabilityEntity.getPredictEndDate(),
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
                    vehicleUnavailabilityEntity.getPredictEndDate(),
                    vehicleUnavailabilityEntity.getEndDate()
            ));

        }
        return unavailabilityListDtos;
    }
    public List<UnavailabilityListDto> getUnavailabilityListByVehicleId(Long vehicleId) {
        List<VehicleUnavailabilityEntity> vehicleUnavailabilityEntities =
                this.getVehicleUnavailabilityEntities();
        List<UnavailabilityListDto> unavailabilityListDtos = new ArrayList<>();

        for(VehicleUnavailabilityEntity vehicleUnavailabilityEntity : vehicleUnavailabilityEntities){
            if(vehicleUnavailabilityEntity.getVehiclesId().equals(vehicleId)){
                Long servicingId = null;
                Long rentingId = null;
                Optional<VehicleRentingEntity> vehicleRentingEntity = vehicleUnavailabilityEntity
                        .getVehicleRentingsById().stream().findFirst();

                Optional<ServicingEntity> servicingEntity = vehicleUnavailabilityEntity
                        .getServicings().stream().findFirst();

                if (vehicleRentingEntity.isPresent())
                    rentingId = vehicleRentingEntity.get().getId();

                if (servicingEntity.isPresent())
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
                        vehicleUnavailabilityEntity.getPredictEndDate(),
                        vehicleUnavailabilityEntity.getEndDate()
                ));
            }

        }
        return unavailabilityListDtos;
    }

    public List<UnfinishedRentingsDto> getUnfinishedVehicleRentingsByUser() {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long personId = personRepository.findByUsername(userPrincipal.getUsername()).getId();

        List<UnfinishedRentingsDto> unfinishedRentingsDtos = new ArrayList<>();
        List<VehicleUnavailabilityEntity> vehicleUnavailabilityEntities = new ArrayList<>(
                vehicleUnavailabilityRepository.findAllByPeopleId(personId));


        for (VehicleUnavailabilityEntity vehicleUnavailabilityEntity : vehicleUnavailabilityEntities) {

            Date now = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Optional<VehicleRentingEntity> byVehicleUnavailabilityId = vehicleRentingRepository
                    .findByVehicleUnavailabilityId(Math.toIntExact(vehicleUnavailabilityEntity.getId()));

            if(byVehicleUnavailabilityId.isPresent()){
                if(now.before(vehicleUnavailabilityEntity.getPredictEndDate())){

                    Integer id = Math.toIntExact(vehicleUnavailabilityEntity.getId());

                    unfinishedRentingsDtos.add(new UnfinishedRentingsDto(
                            vehicleService.getVehicle(vehicleUnavailabilityEntity.getVehiclesId()),
                            vehicleRentingRepository.findByVehicleUnavailabilityId(id).get().getId(),
                            vehicleUnavailabilityEntity.getStartDate(),
                            vehicleUnavailabilityEntity.getPredictEndDate()));
                }
            }


        }
        return unfinishedRentingsDtos;

    }

    public void cancelVehicleRenting(Long id) {
        Date now = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Renting", id));
        VehicleUnavailabilityEntity vehicleUnavailabilityEntity = vehicleUnavailabilityRepository
                .findById((long) vehicleRentingEntity.getVehicleUnavailabilityId())
                .orElseThrow(() -> new IdNotFoundException("Unavailability", id));
        try {
            if(now.before(vehicleUnavailabilityEntity.getStartDate())){
                this.vehicleRentingRepository.deleteById(id);
                this.vehicleUnavailabilityRepository.deleteById(vehicleUnavailabilityEntity.getId());
            }
            else
                throw new RuntimeException();

        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot cancel this renting");
        }

    }

    public void finishVehicleRenting(Long id, FinishVehicleRentingRequest request) {
        Date now = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Renting", id));
        VehicleUnavailabilityEntity vehicleUnavailabilityEntity = vehicleUnavailabilityRepository
                .findById((long) vehicleRentingEntity.getVehicleUnavailabilityId())
                .orElseThrow(() -> new IdNotFoundException("Unavailability", (long) vehicleRentingEntity.getVehicleUnavailabilityId()));
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleUnavailabilityEntity.getVehiclesId())
                .orElseThrow(() -> new IdNotFoundException("Vehicle", id));
        try {
            vehicleUnavailabilityEntity.setEndDate(now);
            vehicleUnavailabilityRepository.save(vehicleUnavailabilityEntity);

            vehicleRentingEntity.setEndmileage(request.getEndMileage());
            vehicleRentingRepository.save(vehicleRentingEntity);

            vehicleEntity.setMileage(request.getEndMileage());
            vehicleRepository.save(vehicleEntity);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot finish this renting");
        }
    }

    public void rentVehicle(Long vehicleId, RentVehicleDto rentVehicleDto) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long personId = personRepository.findByUsername(userPrincipal.getUsername()).getId();
        VehicleDTO vehicleEntity = vehicleService.getVehicle(vehicleId);

        try {
            VehicleUnavailabilityEntity vehicleUnavailabilityEntity = vehicleUnavailabilityRepository.save(
                    new VehicleUnavailabilityEntity(
                            rentVehicleDto.getStartDate(),
                            rentVehicleDto.getPredictEndDate(),
                            null,
                            vehicleId,
                            personId
                    )
            );

            VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.save(
                    new VehicleRentingEntity(
                            vehicleEntity.getMileage(),
                            vehicleEntity.getMileage(),
                            rentVehicleDto.isBusiness(),
                            Math.toIntExact(vehicleUnavailabilityEntity.getId())));

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
