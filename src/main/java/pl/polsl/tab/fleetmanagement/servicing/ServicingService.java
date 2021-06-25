package pl.polsl.tab.fleetmanagement.servicing;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.tab.fleetmanagement.auth.UserPrincipal;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityDto;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableTransactionManagement
public class ServicingService {

    private final ServicingRepository servicingRepository;
    private final PersonRepository personRepository;
    private final VehicleUnavailabilityService vehicleUnavailabilityService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServicingService (
            ServicingRepository servicingRepository,
            PersonRepository personRepository,
            VehicleUnavailabilityService vehicleUnavailabilityService,
            ModelMapper modelMapper
    ) {
        this.servicingRepository = servicingRepository;
        this.personRepository = personRepository;
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

        this.servicingRepository.findAll();
        Optional<ServicingEntity> servicing = this.servicingRepository.findById(id);
        this.servicingRepository.flush();

        if(servicing.isPresent()) return servicing.get();

        throw new IdNotFoundException("Servicing", id);
    }

    /**
     * @param servicingDto json source object
     * @param vehiclesId id of vehicle
     * @param serviceRequestId if of service request. Can be NULL
     * @warning method work with archive data
     * */
    @Transactional
    public ServicingEntity addServicing(ServicingDto servicingDto, Long vehiclesId, Long serviceRequestId) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Date now = new Date(System.currentTimeMillis());
        boolean archive = servicingDto.getEndDate().before(now);
        ServicingEntity resp;

        Long userId = this.personRepository.findByUsername(username).getId();

        try {

            VehicleUnavailabilityDto vud = new VehicleUnavailabilityDto(
                servicingDto.getStartDate(),
                servicingDto.getEndDate(),
                null,
                vehiclesId, userId, null
            );

            Long unavailabilityId = this.vehicleUnavailabilityService.addVehicleUnavailability(vud, archive);

            ServicingEntity servicing = this.modelMapper.map(servicingDto, ServicingEntity.class);
            servicing.setServiceRequestId(serviceRequestId);
            servicing.setVehicleUnavailabilityId(unavailabilityId);

            // if archive set finished as true
            if(archive)
                servicing.setFinished(true);

            resp = this.servicingRepository.saveAndFlush(servicing);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return resp;
    }

    @Transactional
    public ServicingEntity finishServicing(Long id) {
        try {
            // Finish service
            ServicingEntity service = this.getServicingById(id);
            service.setFinished(true);

            // Unblock vehicle (update endDate)
            this.vehicleUnavailabilityService.unlockVehicleBySetEndDate(service.getVehicleUnavailabilityId());

            return this.servicingRepository.saveAndFlush(service);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteServicing(Long id) {
        this.servicingRepository.findAll();
        Optional<ServicingEntity> servicing = this.servicingRepository.findById(id);

        if(servicing.isEmpty()) {
            this.servicingRepository.flush();
            throw new IdNotFoundException("Servicing", id);
        }

        this.servicingRepository.deleteById(id);
        this.servicingRepository.flush();
    }

    public List<ServicingEntity> getServicingByKeeperUsername() {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Long userId = this.personRepository.findByUsername(username).getId();

        return this.getAllServicing()
                .stream()
                .filter(n -> n.getVehicleUnavailability().getPeopleId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<ServicingEntity> getUnfinishedServicingByKeeperUsername() {
        var list = this.getServicingByKeeperUsername();
        return list
                .stream()
                .filter(n -> !n.getFinished())
                .collect(Collectors.toList());
    }

    public List<ServicingEntity> getServicingByVehicleId(Long id) {
        return this.getAllServicing()
                .stream()
                .filter(n -> n.getVehicleUnavailability().getVehiclesId().equals(id))
                .collect(Collectors.toList());
    }

    public List<ServicingEntity> getUnfinishedServicingByVehicleId(Long id) {
        var list = this.getServicingByVehicleId(id);
        return list
                .stream()
                .filter(n -> !n.getFinished())
                .collect(Collectors.toList());
    }
}
