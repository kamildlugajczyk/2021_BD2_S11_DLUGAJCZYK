package pl.polsl.tab.fleetmanagement.rentings;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.auth.UserPrincipal;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.exceptions.NotUniqueException;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityEntity;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityRepository;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleRentingService {

    private final VehicleRentingRepository vehicleRentingRepository;
    private final VehicleUnavailabilityRepository vehicleUnavailabilityRepository;
    private final PersonRepository personRepository;

    public List<VehicleRentingDto> getVehicleRentings() {

        List<VehicleRentingDto> vehicleRentingDtos = new ArrayList<>();
        List<VehicleRentingEntity> vehicleRentingsEntities = new ArrayList<>(vehicleRentingRepository.findAll());

        vehicleRentingsEntities.addAll(vehicleRentingRepository.findAll());

        for (VehicleRentingEntity vehicleRentingEntity : vehicleRentingsEntities) {
            vehicleRentingDtos.add(new VehicleRentingDto(vehicleRentingEntity));
        }
        return vehicleRentingDtos;
    }

    public VehicleRentingDto getVehicleRenting(Long id) {

        VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Vehicle renting", id));

        return new VehicleRentingDto(vehicleRentingEntity);
    }

    public VehicleRentingDto getVehicleRentingByVehicleUnavailability(Long id) {

        VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findByVehicleUnavailabilityId(Math.toIntExact(id))
                .orElseThrow(() -> new IdNotFoundException("Vehicle renting", id));

        return new VehicleRentingDto(vehicleRentingEntity);
    }

    public VehicleRentingDto addVehicleRenting(VehicleRentingDto vehicleRentingDTO) {

        try {
            VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.save(new VehicleRentingEntity(
                    vehicleRentingDTO.getStartmileage(),
                    vehicleRentingDTO.getEndmileage(),
                    vehicleRentingDTO.getIsbusiness(),
                    vehicleRentingDTO.getVehicleUnavailability()));

            return new VehicleRentingDto(vehicleRentingEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO add unique annotation in database script
                    throw new NotUniqueException("Operation cost", "id", vehicleRentingDTO.getId().toString());
                }
            }
            throw new RuntimeException(e);
        }
    }

    public VehicleRentingWithUnavailabilityDto addVehicleRentingWithUnavailability(
            VehicleRentingWithUnavailabilityDto vehicleRentingWithUnavailabilityDto) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PersonEntity personEntity = personRepository.findByUsername(userPrincipal.getUsername());

        try {
            VehicleUnavailabilityEntity vehicleUnavailabilityEntity = vehicleUnavailabilityRepository.save(
                    new VehicleUnavailabilityEntity(
                        vehicleRentingWithUnavailabilityDto.getStartDate(),
                        vehicleRentingWithUnavailabilityDto.getPredictEndDate(),
                        vehicleRentingWithUnavailabilityDto.getEndDate(),
                        vehicleRentingWithUnavailabilityDto.getVehiclesId(),
                        personEntity.getId()
                    )
            );

            VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.save(
                    new VehicleRentingEntity(
                        vehicleRentingWithUnavailabilityDto.getStartmileage(),
                        vehicleRentingWithUnavailabilityDto.getEndmileage(),
                        vehicleRentingWithUnavailabilityDto.isIsbusiness(),
                            Math.toIntExact(vehicleUnavailabilityEntity.getId())));

            return new VehicleRentingWithUnavailabilityDto(vehicleUnavailabilityEntity,vehicleRentingEntity);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }



    @Transactional
    public VehicleRentingDto updateVehicleRenting(Long id, VehicleRentingDto vehicleRentingDTO) {
        Optional<VehicleRentingEntity> vehicleRentingEntity = vehicleRentingRepository.findById(id);

        try {
            vehicleRentingEntity.get().setStartmileage(vehicleRentingDTO.getStartmileage());
            vehicleRentingEntity.get().setEndmileage(vehicleRentingDTO.getEndmileage());
            vehicleRentingEntity.get().setIsbusiness(vehicleRentingDTO.getIsbusiness());
            vehicleRentingEntity.get().setVehicleUnavailabilityId(vehicleRentingDTO.getVehicleUnavailability());
            return new VehicleRentingDto(vehicleRentingRepository.save(vehicleRentingEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO add unique annotation in database script
                    throw new NotUniqueException("Operation cost", "id", vehicleRentingDTO.getId().toString());
                }
            }
            throw new RuntimeException(e);
        }
    }



    public List<VehicleRentingDto> getVehicleRantingsByUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long personId = personRepository.findByUsername(userPrincipal.getUsername()).getId();

        List<VehicleUnavailabilityEntity> vehicleUnavailabilityEntities = new ArrayList<>(
                vehicleUnavailabilityRepository.findAllByPeopleId(personId));
        List<VehicleRentingDto> vehicleRentingDtos = new ArrayList<>();


        for(VehicleUnavailabilityEntity vehicleUnavailabilityEntity: vehicleUnavailabilityEntities){

            try {
                Long id = vehicleUnavailabilityEntity.getId();

                VehicleRentingEntity vehicleRentingEntity = vehicleRentingRepository.findByVehicleUnavailabilityId(Math.toIntExact(id))
                        .orElseThrow(() -> new IdNotFoundException("Vehicle renting", id));

                vehicleRentingDtos.add(new VehicleRentingDto(vehicleRentingEntity));
            }
            catch (RuntimeException ignored){

            }
        }

        return vehicleRentingDtos;
    }


}