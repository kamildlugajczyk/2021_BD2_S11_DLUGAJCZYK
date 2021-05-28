package pl.polsl.tab.fleetmanagement.function;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.exceptions.NotUniqueException;


@RestController
public class FunctionController {

    private FunctionService functionService;

    public FunctionController(FunctionService functionService) {
        this.functionService = functionService;
    }

    @GetMapping("person/function")
    public Iterable<FunctionDTO> getFunctions() {
        return functionService.getFunctions();
    }

    @GetMapping("person/function/{id}")
    public FunctionDTO getFunction(@PathVariable Long id) {
        try {
            return functionService.getFunction(id);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("person/function")
    public FunctionDTO addFunction(@RequestBody FunctionDTO functionDTO) {
        try {
            return functionService.addFunction(functionDTO);
        } catch (NotUniqueException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("person/function/{id}")
    public FunctionDTO updateFunction(@PathVariable Long id, @RequestBody FunctionDTO functionDTO) {
        try {
            return functionService.updateFunction(id, functionDTO);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (NotUniqueException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("person/function/{id}")
    public void deleteFunction(@PathVariable Long id) {
        try {
            functionService.deleteFunction(id);
        } catch (IdNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
