package pl.polsl.tab.fleetmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.models.ServiceTypesEntity;
import pl.polsl.tab.fleetmanagement.services.ServiceTypesService;

import java.sql.SQLException;
import java.util.Optional;

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
    public ResponseEntity<ServiceTypesEntity> getServiceTypesById(@PathVariable Long id) {
        Optional<ServiceTypesEntity> response = this.serviceTypesService.getServiceTypesById(id);
        return response
                .map(serviceTypesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("service/types")
    public ResponseEntity<ServiceTypesEntity> addServiceTypes(@RequestBody ServiceTypesEntity serviceTypesEntity) {
        try {
            ServiceTypesEntity response = this.serviceTypesService.addServiceTypes(serviceTypesEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException  e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);

            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                }
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("service/types/{id}")
    public ResponseEntity<String> deleteServiceTypesById(@PathVariable Long id) {
        try {
            this.serviceTypesService.deleteServiceTypeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
