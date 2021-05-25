//package pl.polsl.tab.fleetmanagement.keeping;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import pl.polsl.tab.fleetmanagement.vehicletype.TypeDTO;
//import pl.polsl.tab.fleetmanagement.vehicletype.TypesEntity;
//
//import java.sql.SQLException;
//import java.util.Optional;
//
//@RestController
//public class KeepingController {
//
//    private KeepingService keepingService;
//
//    public KeepingController(KeepingService keepingService) {
//        this.keepingService = keepingService;
//    }
//
//    @GetMapping("vehicles/types")
//    public Iterable<TypeDTO> getTypes() {
//        return keepingService.getTypes();
//    }
//
//    @GetMapping("vehicles/types/{id}")
//    public ResponseEntity<TypeDTO> getType(@PathVariable Long id) {
//        Optional<TypeDTO> response = keepingService.getType(id);
//
//        return response
//                .map(typesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
//    }
//
//    @PostMapping("vehicles/types")
//    public ResponseEntity<TypeDTO> addType(@RequestBody TypeDTO typeDTO) {
//        try {
//            TypesEntity response = keepingService.addType(typeDTO);
//            typeDTO.setId(response.getId());
//            return ResponseEntity.status(HttpStatus.CREATED).body(typeDTO);
//        } catch (RuntimeException  e) {
//            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
//
//            if (rootCause instanceof SQLException) {
//                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
//                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//                }
//            }
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @PutMapping("vehicles/types/{id}")
//    public ResponseEntity<TypeDTO> updateType(@PathVariable Long id, @RequestBody TypeDTO typeDTO) {
//        Optional<TypeDTO> response = keepingService.updateType(id, typeDTO);
//
//        return response
//                .map(typesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
//    }
//
//    @DeleteMapping("vehicles/types/{id}")
//    public ResponseEntity<TypeDTO> deleteType(@PathVariable Long id) {
//        Optional<TypeDTO> response = keepingService.deleteType(id);
//
//        return response
//                .map(typesEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
//    }
//}
