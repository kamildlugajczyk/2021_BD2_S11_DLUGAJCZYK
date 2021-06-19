package pl.polsl.tab.fleetmanagement.vehicle;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.KeeperNotFoundException;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;
import pl.polsl.tab.fleetmanagement.keeping.KeepingRequest;
import pl.polsl.tab.fleetmanagement.person.PersonDTO;

import java.util.List;

@RestController
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/vehicle")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/vehicle/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public VehicleDTO getVehicle(@PathVariable Long id) {
        try {
            return vehicleService.getVehicle(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/vehicle")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public VehicleDTO addVehicle(@RequestBody VehicleDTORequest vehicleDTO) {
        try {
            return vehicleService.addVehicle(vehicleDTO);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IdNotFoundInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/vehicle/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public VehicleDTO updateVehicle(@PathVariable Long id, @RequestBody VehicleDTORequest vehicleDTO) {
        try {
            return vehicleService.updateVehicle(id, vehicleDTO);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/vehicle/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/vehicle/{id}/keeping")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<KeepingDTO> getVehiclesKeepings(@PathVariable Long id) {
        try {
            return vehicleService.getVehiclesKeepings(id);
        } catch (IdNotFoundInDatabaseException e)
        {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/vehicle/{id}/keeper")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PersonDTO getVehiclesKeeper(@PathVariable Long id) {
        try {
            return vehicleService.getVehiclesKeeper(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (KeeperNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/vehicle/{id}/keeping")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void changeVehiclesKeeper(@PathVariable Long id, @RequestBody KeepingRequest request) {
        try {
            vehicleService.changeVehiclesKeeper(id, request);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IdNotFoundInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}