package pl.polsl.tab.fleetmanagement.servicing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;

import java.util.List;

@RestController
@RequestMapping("service")
public class ServicingController {

    private final ServicingService servicingService;

    @Autowired
    public ServicingController(ServicingService servicingService) {
        this.servicingService = servicingService;
    }

    @GetMapping("")
    public List<ServicingEntity> getAllServicing() {
        return this.servicingService.getAllServicing();
    }

    @GetMapping("finished")
    public List<ServicingEntity> getAllFinishedServicing() {
        return this.servicingService.getAllFinishedServicing();
    }

    @GetMapping("unfinished")
    public List<ServicingEntity> getAllUnfinishedServicing() {
        return this.servicingService.getAllUnfinishedServicing();
    }

    @GetMapping("{id}")
    public ServicingEntity getServicingById(@PathVariable Long id) {
        try {
            return this.servicingService.getServicingById(id);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/keeper")
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

    @PatchMapping("/keeper/{id}/finish")
    public ServicingEntity finishServicing(@PathVariable Long id) {
        try {
            return this.servicingService.finishServicing(id);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/keeper/{id}")
    public void deleteServicing(@PathVariable Long id) {
        try {
            this.servicingService.deleteServicing(id);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
