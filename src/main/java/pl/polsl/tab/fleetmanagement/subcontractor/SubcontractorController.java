package pl.polsl.tab.fleetmanagement.subcontractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;

@RestController
public class SubcontractorController {

    private final SubcontractorService subcontractorService;

    @Autowired
    public SubcontractorController(SubcontractorService subcontractorService) {
        this.subcontractorService = subcontractorService;
    }

    @GetMapping("service/subcontractor/")
    public Iterable<SubcontractorEntity> getSubcontractors() {
        return this.subcontractorService.getSubcontractors();
    }

    @GetMapping("service/subcontractor/{id}")
    public SubcontractorEntity getSubcontractorById(@PathVariable Long id) {
        try {
            return this.subcontractorService.getSubcontractorById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("service/subcontractor/")
    public SubcontractorEntity addSubcontractor(@RequestBody SubcontractorDto subcontractor) {
        try {
            return this.subcontractorService.addSubcontractor(subcontractor);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("service/subcontractor/{id}")
    public SubcontractorEntity updateSubcontractorById(@PathVariable Long id, @RequestBody SubcontractorDto subcontractor) {
        try {
            return this.subcontractorService.updateSubcontractorById(id, subcontractor);
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

    @DeleteMapping("service/subcontractor/{id}")
    public void deleteSubcontractorById(@PathVariable Long id) {
       try {
           this.subcontractorService.deleteSubcontractorById(id);
       } catch (IdNotFoundInDatabaseException e) {
           System.out.println(e.getMessage());
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
       }
    }
}
