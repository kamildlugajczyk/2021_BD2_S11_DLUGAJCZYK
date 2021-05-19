package pl.polsl.tab.fleetmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.models.SubcontractorsEntity;
import pl.polsl.tab.fleetmanagement.services.SubcontractorsService;

import java.sql.SQLException;
import java.util.Optional;

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
    public ResponseEntity<SubcontractorsEntity> getSubcontractorById(@PathVariable Long id) {
        Optional<SubcontractorsEntity> response = this.subcontractorsService.getSubcontractorById(id);
        return response
                .map(subcontractorsEntity -> ResponseEntity.status(HttpStatus.OK).body(subcontractorsEntity))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("service/subcontractors/")
    public ResponseEntity<SubcontractorsEntity> addSubcontractor(@RequestBody SubcontractorsEntity subcontractor) {
        try {
            SubcontractorsEntity response = this.subcontractorsService.addSubcontractor(subcontractor);
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

    @DeleteMapping("service/subcontractors/{id}")
    public ResponseEntity<String> deleteSubcontractorById(@PathVariable Long id) {
       try {
           this.subcontractorsService.deleteSubcontractorById(id);
           return ResponseEntity.status(HttpStatus.OK).body(null);
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
    }
}
