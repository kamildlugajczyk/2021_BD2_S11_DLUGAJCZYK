package pl.polsl.tab.fleetmanagement.servicerequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.tab.fleetmanagement.auth.JwtAuthenticationRequest;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;
import pl.polsl.tab.fleetmanagement.servicing.ServicingService;
import pl.polsl.tab.fleetmanagement.servicing.ServicingDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableTransactionManagement
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final PersonRepository personRepository;
    private final ServicingService servicingService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceRequestService (
            ServiceRequestRepository serviceRequestRepository,
            ServicingService servicingService,
            PersonRepository personRepository,
            ModelMapper modelMapper
    ) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.personRepository = personRepository;
        this.servicingService = servicingService;
        this.modelMapper = modelMapper;
    }

    public List<ServiceRequestEntity> getAllServicesRequest() {
        return this.serviceRequestRepository.findAll();
    }

    public List<ServiceRequestEntity> getAllUnprocessedServicesRequest() {
        List<ServiceRequestEntity> allItems = this.getAllServicesRequest();
        return allItems.stream().filter(i -> !i.getProcessed()).collect(Collectors.toList());
    }

    public List<ServiceRequestEntity> getUnprocessedServicesRequestPersonal(String username) {

        Long currentKeeperId = this.personRepository.findByUsername(username).getId();

        List<ServiceRequestEntity> response = new LinkedList<>();

        var allItems = this.getAllServicesRequest();

        var unprocessed = allItems
                .stream()
                .filter(i -> !i.getProcessed())
                .collect(Collectors.toList());

        for (ServiceRequestEntity serviceRequest: unprocessed) {
            var allKeepingData = serviceRequest.getVehiclesByVehiclesId().getKeepingsById();

            for (KeepingEntity keepingItem: allKeepingData) {
                if(keepingItem.getEnddate() != null) continue;

                if(keepingItem.getPeopleId() == currentKeeperId) {
                    response.add(serviceRequest);
                    break;
                }

            }
        }

        return response;

    }

    public List<ServiceRequestEntity> getAllProcessedServicesRequest() {
        List<ServiceRequestEntity> allItems = this.getAllServicesRequest();
        return allItems.stream().filter(ServiceRequestEntity::getProcessed).collect(Collectors.toList());
    }

    public ServiceRequestEntity getServiceRequestById(Long id) {
        return this.serviceRequestRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundException("Service Request", id));
    }

    public ServiceRequestEntity addServiceRequest(ServiceRequestDto requestDto, String username) {

        Long userId = this.personRepository.findByUsername(username).getId();

        ServiceRequestEntity sre = this.modelMapper.map(requestDto, ServiceRequestEntity.class);
        sre.setProcessed(false);
        sre.setPeopleId(userId);
        return this.serviceRequestRepository.save(sre);
    }

    /**
     * Method try processed service request. If no error request is set as processed and
     * servicing information is adding to database
     * */
    @Transactional
    public ServicingEntity executeServiceRequest(ServicingDto servicingDto, Long id, String username) {
        try {
            ServiceRequestEntity sre = this.getServiceRequestById(id);

            if(sre.getProcessed())
                throw new RuntimeException("Service request already processed");

            // Add servicing to database
            ServicingEntity se = this.servicingService.addServicing(servicingDto, username, sre.getVehiclesId(), id);

            // Set request as processed
            sre.setProcessed(true);
            this.serviceRequestRepository.save(sre);

            return se;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteServiceRequest(Long id) {
        try {
            this.serviceRequestRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new IdNotFoundException("Service Request", id);
        }
    }
}
