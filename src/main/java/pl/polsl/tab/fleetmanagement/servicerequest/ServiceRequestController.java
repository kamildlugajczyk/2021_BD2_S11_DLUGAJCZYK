package pl.polsl.tab.fleetmanagement.servicerequest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.auth.JwtAuthenticationRequest;
import pl.polsl.tab.fleetmanagement.servicing.ServicingDto;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;

import java.util.List;

@RestController
@RequestMapping("service/request")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @Autowired
    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping()
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServiceRequestEntity> getAllServicesRequest() {
        return this.serviceRequestService.getAllServicesRequest();
    }

    @GetMapping("unprocessed")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServiceRequestEntity> getAllUnprocessedServicesRequest() {
        return this.serviceRequestService.getAllUnprocessedServicesRequest();
    }

    @GetMapping("unprocessed/personal/{username}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServiceRequestEntity> getUnprocessedServicesRequestPersonal(@PathVariable String username) {
        return this.serviceRequestService.getUnprocessedServicesRequestPersonal(username);
    }

    @GetMapping("processed")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServiceRequestEntity> getAllProcessedServicesRequest() {
        return this.serviceRequestService.getAllProcessedServicesRequest();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServiceRequestEntity getServicesRequest(@PathVariable Long id) {
        return this.serviceRequestService.getServiceRequestById(id);
    }

    @PostMapping("")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
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
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServicingEntity executeServiceRequest(@RequestBody ServicingDto servicingDto, @PathVariable Long id) {
        return this.serviceRequestService.executeServiceRequest(servicingDto, id);
    }

    @DeleteMapping("keeper/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteServicesRequest(@PathVariable Long id) {
        this.serviceRequestService.deleteServiceRequest(id);
    }
}
