package pl.polsl.tab.fleetmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.dto.ServiceRequestDto;
import pl.polsl.tab.fleetmanagement.dto.ServicingDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServiceRequestEntity;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;
import pl.polsl.tab.fleetmanagement.services.ServiceRequestService;

import java.util.List;

@RestController
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @Autowired
    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    /**
     * Check whether request exists in database
     * @param id service request id
     * @return true - exists, otherwise false
    **/
    @RequestMapping(value = "service/request/exist/{id}", method = RequestMethod.HEAD)
    public Boolean exist(@PathVariable Long id) {
        try {
            this.serviceRequestService.getServiceRequestById(id);
            return true;
        } catch (Error ignored) {
            return false;
        }
    }

    @GetMapping("service/request")
    public List<ServiceRequestEntity> getAllServicesRequest() {
        return this.serviceRequestService.getAllServicesRequest();
    }

    @GetMapping("service/request/unprocessed")
    public List<ServiceRequestEntity> getAllUnprocessedServicesRequest() {
        return this.serviceRequestService.getAllUnprocessedServicesRequest();
    }

    @GetMapping("service/request/processed")
    public List<ServiceRequestEntity> getAllProcessedServicesRequest() {
        return this.serviceRequestService.getAllProcessedServicesRequest();
    }

    @GetMapping("service/request/{id}")
    public ServiceRequestEntity getServicesRequest(@PathVariable Long id) {
        try {
            return this.serviceRequestService.getServiceRequestById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("service/request/")
    public ServiceRequestEntity addServiceRequest(@RequestBody ServiceRequestDto request) {
        try {
            return this.serviceRequestService.addServiceRequest(request);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Execute service request (set service request as process)
     * @param id service request id
     * @param servicingDto servicing data
     * @return servicing json object
     **/
    @PatchMapping("service/request/execute/{id}")
    public ServicingEntity executeServiceRequest(@RequestBody ServicingDto servicingDto, @PathVariable Long id) {
        try {
            return this.serviceRequestService.executeServiceRequest(servicingDto, id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("service/request/{id}")
    public void deleteServicesRequest(@PathVariable Long id) {
        try {
            this.serviceRequestService.deleteServiceRequest(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
