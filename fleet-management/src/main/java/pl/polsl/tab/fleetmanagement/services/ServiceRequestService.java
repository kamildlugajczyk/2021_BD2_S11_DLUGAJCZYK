package pl.polsl.tab.fleetmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServiceRequestEntity;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;
import pl.polsl.tab.fleetmanagement.repositories.ServiceRequestRepository;

import java.util.List;

// TODO permissions

@Component
@EnableTransactionManagement
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ServicingService servicingService;

    @Autowired
    public ServiceRequestService (
            ServiceRequestRepository serviceRequestRepository,
            ServicingService servicingService
    ) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.servicingService = servicingService;
    }

    public List<ServiceRequestEntity> getAllServicesRequest() {
        return this.serviceRequestRepository.findAll();
    }

    public ServiceRequestEntity getServiceRequestById(Long id) {
        return this.serviceRequestRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Service Request (" + id + ") not exists"));
    }

    public ServiceRequestEntity addServiceRequest(ServiceRequestEntity request) {
        return this.serviceRequestRepository.save(request);
    }

    /**
     * Method check whether vehicle is availability.
     * If true method update information about unavailability and servicing
     * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ServicingEntity executeServiceRequest(ServicingEntity servicing, Long id) {
        ServicingEntity res;

        try {
            this.requestProcess(id); // It also check whether id is correct, otherwise exception
            // Add servicing to database
            res = this.servicingService.addServicing(servicing, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    private void requestProcess(Long id) {
        ServiceRequestEntity obj = this.getServiceRequestById(id);
        // TODO
        // if(obj.getProcess())
        // throw new RuntimeException("Service request already process");
        // entity obj.setProcess(true);
        this.serviceRequestRepository.save(obj);
    }

    public void deleteServiceRequest(Long id) {
        try {
            this.serviceRequestRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new IdNotFoundInDatabaseException("Service Request (" + id + ") not exists");
        }
    }
}
