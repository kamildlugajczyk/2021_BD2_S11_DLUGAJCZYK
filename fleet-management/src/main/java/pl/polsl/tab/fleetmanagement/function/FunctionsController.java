package pl.polsl.tab.fleetmanagement.function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class FunctionsController {

    private FunctionsService functionsService;

    public FunctionsController(FunctionsService functionsService) {
        this.functionsService = functionsService;
    }

    @GetMapping("people/functions")
    public Iterable<FunctionDTO> getFunctions() {
        return functionsService.getFunctions();
    }

    @GetMapping("people/functions/{id}")
    public ResponseEntity<FunctionDTO> getFunction(@PathVariable Long id) {
        Optional<FunctionDTO> response = functionsService.getFunction(id);

        return response
                .map(functionsEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("people/functions")
    public ResponseEntity<FunctionDTO> addFunction(@RequestBody FunctionDTO functionDTO) {
        try {
            FunctionsEntity response = functionsService.addFunction(functionDTO);
            functionDTO.setId(response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(functionDTO);
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

    @PutMapping("people/functions/{id}")
    public ResponseEntity<FunctionDTO> updateFunction(@PathVariable Long id, @RequestBody FunctionDTO functionDTO) {
        Optional<FunctionDTO> response = functionsService.updateFunction(id, functionDTO);

        return response
                .map(functionsEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("people/functions/{id}")
    public ResponseEntity<FunctionDTO> deleteFunction(@PathVariable Long id) {
        Optional<FunctionDTO> response = functionsService.deleteFunction(id);

        return response
                .map(functionsEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
