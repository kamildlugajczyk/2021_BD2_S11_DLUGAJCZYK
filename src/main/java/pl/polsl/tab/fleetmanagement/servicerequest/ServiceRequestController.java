package pl.polsl.tab.fleetmanagement.servicerequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.servicing.ServicingDto;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;

import java.util.List;

@RestController
@RequestMapping("service/request")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @Autowired
    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping()
    public List<ServiceRequestEntity> getAllServicesRequest() {
        return this.serviceRequestService.getAllServicesRequest();
    }

    @GetMapping("unprocessed")
    public List<ServiceRequestEntity> getAllUnprocessedServicesRequest() {
        return this.serviceRequestService.getAllUnprocessedServicesRequest();
    }

    @GetMapping("processed")
    public List<ServiceRequestEntity> getAllProcessedServicesRequest() {
        return this.serviceRequestService.getAllProcessedServicesRequest();
    }

    @GetMapping("{id}")
    public ServiceRequestEntity getServicesRequest(@PathVariable Long id) {
        return this.serviceRequestService.getServiceRequestById(id);
    }

    @PostMapping("")
    public ServiceRequestEntity addServiceRequest(@RequestBody ServiceRequestDto request) {
        return this.serviceRequestService.addServiceRequest(request);
    }

    /**
     * Execute service request (set service request as process)
     * @param id service request id
     * @param servicingDto servicing data
     * @return servicing json object
     **/
    @PatchMapping("keeper/execute/{id}")
    public ServicingEntity executeServiceRequest(@RequestBody ServicingDto servicingDto, @PathVariable Long id) {
        return this.serviceRequestService.executeServiceRequest(servicingDto, id);
    }

    @DeleteMapping("keeper/{id}")
    public void deleteServicesRequest(@PathVariable Long id) {
        this.serviceRequestService.deleteServiceRequest(id);
    }
}
