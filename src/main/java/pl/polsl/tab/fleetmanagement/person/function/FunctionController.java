package pl.polsl.tab.fleetmanagement.person.function;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.other.PostValue;


@RestController
public class FunctionController {

    private FunctionService functionService;

    public FunctionController(FunctionService functionService) {
        this.functionService = functionService;
    }

    @GetMapping("person/function")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<FunctionDTO> getFunctions() {
        return functionService.getFunctions();
    }

    @GetMapping("person/function/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public FunctionDTO getFunction(@PathVariable Long id) {
        try {
            return functionService.getFunction(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("person/function")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public FunctionDTO addFunction(@RequestBody PostValue<String> function) {
        try {
            return functionService.addFunction(function);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("person/function/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public FunctionDTO updateFunction(@PathVariable Long id, @RequestBody PostValue<String> function) {
        try {
            return functionService.updateFunction(id, function);
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

    @DeleteMapping("person/function/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteFunction(@PathVariable Long id) {
        try {
            functionService.deleteFunction(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
