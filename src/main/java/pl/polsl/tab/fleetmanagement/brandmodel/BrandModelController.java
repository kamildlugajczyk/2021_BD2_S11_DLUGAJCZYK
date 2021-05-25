package pl.polsl.tab.fleetmanagement.brandmodel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;



@RestController
public class BrandModelController {

    private BrandModelService brandModelService;

    public BrandModelController(BrandModelService brandModelService) {
        this.brandModelService = brandModelService;
    }

    @GetMapping("vehicle/brandmodel")
    public Iterable<BrandModelDTO> getBrandModels() {
        return brandModelService.getBrandsModels();
    }

    @GetMapping("vehicle/brandmodel/{id}")
    public BrandModelDTO getBrandModel(@PathVariable Long id) {
        try {
            return brandModelService.getBrandModel(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("vehicle/brandmodel")
    public BrandModelDTO addBrandModel(@RequestBody BrandModelDTO brandModelDTO) {
        try {
            return brandModelService.addBrandModel(brandModelDTO);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException  e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("vehicle/brandmodel/{id}")
    public BrandModelDTO updateBrandModel(@PathVariable Long id, @RequestBody BrandModelDTO brandModelDTO) {
        try {
            return brandModelService.updateBrandModel(id, brandModelDTO);
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

    @DeleteMapping("vehicle/brandmodel/{id}")
    public void deleteBrandModel(@PathVariable Long id) {
        try {
            brandModelService.deleteBrandModel(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
