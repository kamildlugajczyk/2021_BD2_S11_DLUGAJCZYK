package pl.polsl.tab.fleetmanagement.vehicle.purpose;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.other.PostValue;


@RestController
public class PurposeController {

    private PurposeService purposeService;

    public PurposeController(PurposeService purposeService) {
        this.purposeService = purposeService;
    }

    @GetMapping("vehicle/purpose")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<PurposeDTO> getPurposes() {
        return purposeService.getPurposes();
    }

    @GetMapping("vehicle/purpose/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PurposeDTO getPurpose(@PathVariable Long id) {
        try {
            return purposeService.getPurpose(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("vehicle/purpose")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PurposeDTO addPurpose(@RequestBody PostValue<String> purpose) {
        try {
            return purposeService.addPurpose(purpose);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("vehicle/purpose/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PurposeDTO updatePurpose(@PathVariable Long id, @RequestBody PostValue<String> purpose) {
        try {
            return purposeService.updatePurpose(id, purpose);
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

    @DeleteMapping("vehicle/purpose/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deletePurpose(@PathVariable Long id) {
        try {
            purposeService.deletePurpose(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
