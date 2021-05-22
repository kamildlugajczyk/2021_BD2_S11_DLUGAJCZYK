package pl.polsl.tab.fleetmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.dto.ServicingDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;
import pl.polsl.tab.fleetmanagement.services.ServicingService;

import java.util.List;

@RestController
public class ServicingController {

    private final ServicingService servicingService;

    @Autowired
    public ServicingController(ServicingService servicingService) {
        this.servicingService = servicingService;
    }

    /**
     * Check whether service exists in database
     * @param id service id
     * @return true - exists, otherwise false
     **/
    @RequestMapping(value = "service/exist/{id}", method = RequestMethod.HEAD)
    public Boolean exist(@PathVariable Long id) {
        try {
            this.servicingService.getServicingById(id);
            return true;
        } catch (Error ignored) {
            return false;
        }
    }

    @GetMapping("service/")
    public List<ServicingEntity> getAllServicing() {
        return this.servicingService.getAllServicing();
    }

    @GetMapping("service/finished")
    public List<ServicingEntity> getAllFinishedServicing() {
        return this.servicingService.getAllFinishedServicing();
    }

    @GetMapping("service/unfinished")
    public List<ServicingEntity> getAllUnfinishedServicing() {
        return this.servicingService.getAllUnfinishedServicing();
    }

    @GetMapping("service/{id}")
    public ServicingEntity getServicingById(@PathVariable Long id) {
        try {
            return this.servicingService.getServicingById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("service/")
    public ServicingEntity addServicing(
            @RequestBody ServicingDto request,
            @RequestParam(name="keeperId") Long keeperId,
            @RequestParam(name="vehicleId") Long vehicleId) {
        try {
            return this.servicingService.addServicing(request, keeperId, vehicleId, null);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PatchMapping("service/{id}/finish")
    public ServicingEntity finishServicing(@PathVariable Long id) {
        try {
            return this.servicingService.finishServicing(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("service/{id}")
    public void deleteServicing(@PathVariable Long id) {
        try {
            this.servicingService.deleteServicing(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
