package pl.polsl.tab.fleetmanagement.vehiclepurpose;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class PurposesController {

    private PurposesService purposesService;

    public PurposesController(PurposesService purposesService) {
        this.purposesService = purposesService;
    }

    @GetMapping("vehicles/purposes")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<PurposeDTO> getPurposes() {
        return purposesService.getPurposes();
    }

    @GetMapping("vehicles/purposes/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<PurposeDTO> getPurpose(@PathVariable Long id) {
        Optional<PurposeDTO> response = purposesService.getPurpose(id);

        return response
                .map(purposesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("vehicles/purposes")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<PurposeDTO> addPurpose(@RequestBody PurposeDTO purposeDTO) {
        try {
            PurposesEntity response = purposesService.addPurpose(purposeDTO);
            purposeDTO.setId(response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(purposeDTO);
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

    @PutMapping("vehicles/purposes/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<PurposeDTO> updatePurpose(@PathVariable Long id, @RequestBody PurposeDTO purposeDTO) {
        Optional<PurposeDTO> response = purposesService.updatePurpose(id, purposeDTO);

        return response
                .map(purposesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("vehicles/purposes/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<PurposeDTO> deletePurpose(@PathVariable Long id) {
        Optional<PurposeDTO> response = purposesService.deletePurpose(id);

        return response
                .map(purposesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
