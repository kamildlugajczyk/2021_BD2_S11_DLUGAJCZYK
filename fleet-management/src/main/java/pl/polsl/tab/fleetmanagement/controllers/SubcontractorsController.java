package pl.polsl.tab.fleetmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.SubcontractorsEntity;
import pl.polsl.tab.fleetmanagement.services.SubcontractorsService;

@RestController
public class SubcontractorsController {

    private final SubcontractorsService subcontractorsService;

    @Autowired
    public SubcontractorsController(SubcontractorsService subcontractorsService) {
        this.subcontractorsService = subcontractorsService;
    }

    @GetMapping("service/subcontractors/")
    public Iterable<SubcontractorsEntity> getSubcontractors() {
        return this.subcontractorsService.getSubcontractors();
    }

    @GetMapping("service/subcontractors/{id}")
    public SubcontractorsEntity getSubcontractorById(@PathVariable Long id) {
        try {
            return this.subcontractorsService.getSubcontractorById(id);
        } catch (IdNotFoundInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("service/subcontractors/")
    public SubcontractorsEntity addSubcontractor(@RequestBody SubcontractorsEntity subcontractor) {
        try {
            return this.subcontractorsService.addSubcontractor(subcontractor);
        } catch (ItemExistsInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException  e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("service/subcontractors/{id}")
    public SubcontractorsEntity updateSubcontractorById(@RequestBody SubcontractorsEntity subcontractor, @PathVariable Long id) {
        try {
            return this.subcontractorsService.updateSubcontractorById(subcontractor, id);
        } catch (IdNotFoundInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (ItemExistsInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("service/subcontractors/{id}")
    public void deleteSubcontractorById(@PathVariable Long id) {
       try {
           this.subcontractorsService.deleteSubcontractorById(id);
       } catch (IdNotFoundInDatabaseException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
       }
    }
}