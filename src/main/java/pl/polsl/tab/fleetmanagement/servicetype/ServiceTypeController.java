package pl.polsl.tab.fleetmanagement.servicetype;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.other.PostValue;


@RestController
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @Autowired
    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping("service/type")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<ServiceTypeEntity> getServiceTypes() {
        return this.serviceTypeService.getServiceTypes();
    }

    @GetMapping("service/type/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServiceTypeEntity getServiceTypesById(@PathVariable Long id) {
        return this.serviceTypeService.getServiceTypesById(id);
    }

    @PostMapping("service/type")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServiceTypeEntity addServiceTypes(@RequestBody PostValue<String> service) {
        return this.serviceTypeService.addServiceTypes(service);
    }

    @PutMapping("service/type/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServiceTypeEntity updateServiceType(@PathVariable Long id, @RequestBody PostValue<String> service) {
        return this.serviceTypeService.updateServiceType(id, service);
    }

    @DeleteMapping("service/type/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteServiceTypesById(@PathVariable Long id) {
        this.serviceTypeService.deleteServiceTypeById(id);
    }

}
