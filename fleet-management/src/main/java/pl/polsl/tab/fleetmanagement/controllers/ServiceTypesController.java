package pl.polsl.tab.fleetmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServiceTypesEntity;
import pl.polsl.tab.fleetmanagement.services.ServiceTypesService;

@RestController
public class ServiceTypesController {

    private final ServiceTypesService serviceTypesService;

    @Autowired
    public ServiceTypesController(ServiceTypesService serviceTypesService) {
        this.serviceTypesService = serviceTypesService;
    }

    @GetMapping("service/types")
    public Iterable<ServiceTypesEntity> getServiceTypes() {
        return this.serviceTypesService.getServiceTypes();
    }

    @GetMapping("service/types/{id}")
    public ServiceTypesEntity getServiceTypesById(@PathVariable Long id) {
        try {
            return this.serviceTypesService.getServiceTypesById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("service/types")
    public ServiceTypesEntity addServiceTypes(@RequestBody ServiceTypesEntity serviceTypesEntity) {
        try {
            return this.serviceTypesService.addServiceTypes(serviceTypesEntity);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("service/types/{id}")
    public ServiceTypesEntity updateServiceType(@RequestBody String name, @PathVariable Long id) {
        try {
            return this.serviceTypesService.updateServiceType(name, id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("service/types/{id}")
    public void deleteServiceTypesById(@PathVariable Long id) {
        try {
            this.serviceTypesService.deleteServiceTypeById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
