package pl.polsl.tab.fleetmanagement.vehicle.type;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;

@RestController
public class TypeController {

    private TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("vehicle/type")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<TypeDTO> getTypes() {
        return typeService.getTypes();
    }

    @GetMapping("vehicle/type/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public TypeDTO getType(@PathVariable Long id) {
        try {
            return typeService.getType(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("vehicle/type")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public TypeDTO addType(@RequestBody TypeDTO typeDTO) {
        try {
            return typeService.addType(typeDTO);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("vehicle/type/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public TypeDTO updateType(@PathVariable Long id, @RequestBody TypeDTO typeDTO) {
        try {
            return typeService.updateType(id, typeDTO);
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

    @DeleteMapping("vehicle/type/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteType(@PathVariable Long id) {
        try {
            typeService.deleteType(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
