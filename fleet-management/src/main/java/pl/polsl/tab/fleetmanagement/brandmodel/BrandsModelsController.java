package pl.polsl.tab.fleetmanagement.brandmodel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.vehicletype.TypesEntity;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class BrandsModelsController {

    private BrandsModelsService brandsModelsService;

    public BrandsModelsController(BrandsModelsService brandsModelsService) {
        this.brandsModelsService = brandsModelsService;
    }

    @GetMapping("vehicles/brandsmodels")
    public Iterable<BrandModelDTO> getBrandsModels() {
        return brandsModelsService.getBrandsModels();
    }

    @GetMapping("vehicles/brandsmodels/{id}")
    public ResponseEntity<BrandModelDTO> getType(@PathVariable Long id) {
        Optional<BrandModelDTO> response = brandsModelsService.getBrandModel(id);

        return response
                .map(brandsModelsEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("vehicles/brandsmodels")
    public ResponseEntity<BrandModelDTO> addBrandsModels(@RequestBody BrandModelDTO brandModelDTO) {
        try {
            BrandsModelsEntity response = brandsModelsService.addBrandModel(brandModelDTO);
            brandModelDTO.setId(response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(brandModelDTO);
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

    @PutMapping("vehicles/brandsmodels/{id}")
    public ResponseEntity<BrandModelDTO> updateBrandsModels(@PathVariable Long id, @RequestBody BrandModelDTO brandModelDTO) {
        Optional<BrandModelDTO> response = brandsModelsService.updateBrandModel(id, brandModelDTO);

        return response
                .map(brandsModelsEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("vehicles/brandsmodels/{id}")
    public ResponseEntity<BrandModelDTO> deleteBrandsModels(@PathVariable Long id) {
        Optional<BrandModelDTO> response = brandsModelsService.deleteBrandModel(id);

        return response
                .map(brandsModelsEntity -> ResponseEntity.status(HttpStatus.OK).body(response.get()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
