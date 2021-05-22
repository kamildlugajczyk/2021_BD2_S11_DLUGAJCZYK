package pl.polsl.tab.fleetmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.tab.fleetmanagement.dto.ServicingDto;
import pl.polsl.tab.fleetmanagement.dto.VehicleUnavailabilityDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;
import pl.polsl.tab.fleetmanagement.repositories.ServicingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// TODO permissions

@Component
@EnableTransactionManagement
public class ServicingService {

    private final ServicingRepository servicingRepository;
    private final VehicleUnavailabilityService vehicleUnavailabilityService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServicingService (
            ServicingRepository servicingRepository,
            VehicleUnavailabilityService vehicleUnavailabilityService,
            ModelMapper modelMapper
    ) {
        this.servicingRepository = servicingRepository;
        this.vehicleUnavailabilityService = vehicleUnavailabilityService;
        this.modelMapper = modelMapper;
    }

    public List<ServicingEntity> getAllServicing() {
        return this.servicingRepository.findAll();
    }

    public List<ServicingEntity> getAllFinishedServicing() {
        List<ServicingEntity> allItems = this.getAllServicing();
        return allItems.stream().filter(ServicingEntity::getFinished).collect(Collectors.toList());
    }

    public List<ServicingEntity> getAllUnfinishedServicing() {
        List<ServicingEntity> allItems = this.getAllServicing();
        return allItems.stream().filter(i -> !i.getFinished()).collect(Collectors.toList());
    }

    public ServicingEntity getServicingById(Long id) {
        return this.servicingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Servicing " + id + " not exists"));
    }

    /**
     * @param servicingDto
     * @param personId id of keeper
     * @param vehiclesId id of vehicle
     * @param serviceRequestId if of service request. Can be NULL
     * */
    @Transactional
    public ServicingEntity addServicing(ServicingDto servicingDto, Long personId, Long vehiclesId, Long serviceRequestId) {
        ServicingEntity resp;

        try {

            VehicleUnavailabilityDto vud = new VehicleUnavailabilityDto(
                servicingDto.getStartDate(),
                servicingDto.getEndDate(),
                "0",
                vehiclesId,
                personId
            );
            
            Long unavailabilityId = this.vehicleUnavailabilityService.addVehicleUnavailability(vud);

            ServicingEntity servicing = this.modelMapper.map(servicingDto, ServicingEntity.class);
            servicing.setServiceRequestId(serviceRequestId);
            servicing.setVehicleUnavailabilityId(unavailabilityId);

            resp = this.servicingRepository.save(servicing);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return resp;
    }

    @Transactional
    public ServicingEntity finishServicing(Long id) {
        LocalDate localDate = LocalDate.now();

        ServicingEntity service = this.getServicingById(id);
        service.setEndDate(java.sql.Date.valueOf(localDate));
        service.setFinished(true);

        // TODO unblock vehicle

        return this.servicingRepository.save(service);
    }

    public void deleteServicing(Long id) {
        try {
            this.servicingRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new IdNotFoundInDatabaseException("Servicing " + id + " not exists");
        }
    }
}
