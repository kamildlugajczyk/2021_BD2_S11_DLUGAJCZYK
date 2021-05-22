package pl.polsl.tab.fleetmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.tab.fleetmanagement.dto.ServiceRequestDto;
import pl.polsl.tab.fleetmanagement.dto.ServicingDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServiceRequestEntity;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;
import pl.polsl.tab.fleetmanagement.repositories.ServiceRequestRepository;

import java.util.List;
import java.util.stream.Collectors;

// TODO permissions

@Component
@EnableTransactionManagement
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServicingService servicingService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceRequestService (
            ServiceRequestRepository serviceRequestRepository,
            ServicingService servicingService,
            ModelMapper modelMapper
    ) {
        this.serviceRequestRepository = serviceRequestRepository;
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

    public List<ServiceRequestEntity> getAllProcessedServicesRequest() {
        List<ServiceRequestEntity> allItems = this.getAllServicesRequest();
        return allItems.stream().filter(ServiceRequestEntity::getProcessed).collect(Collectors.toList());
    }

    public ServiceRequestEntity getServiceRequestById(Long id) {
        return this.serviceRequestRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Service Request (" + id + ") not exists"));
    }

    public ServiceRequestEntity addServiceRequest(ServiceRequestDto requestDto) {
        ServiceRequestEntity sre = this.modelMapper.map(requestDto, ServiceRequestEntity.class);
        sre.setProcessed(false);
        return this.serviceRequestRepository.save(sre);
    }

    /**
     * Method try processed service request. If no error request is set as processed and
     * servicing information is adding to database
     * */
    @Transactional
    public ServicingEntity executeServiceRequest(ServicingDto servicingDto, Long id) {
        try {
            ServiceRequestEntity sre = this.getServiceRequestById(id);

            if(sre.getProcessed())
                throw new RuntimeException("Service request already processed");

            // Add servicing to database
            ServicingEntity se = this.servicingService.addServicing(servicingDto, sre.getPeopleId(), sre.getVehiclesId(), id);

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
            throw new IdNotFoundInDatabaseException("Service Request (" + id + ") not exists");
        }
    }
}
