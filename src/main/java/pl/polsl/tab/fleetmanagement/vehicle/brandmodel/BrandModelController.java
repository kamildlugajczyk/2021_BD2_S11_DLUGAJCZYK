package pl.polsl.tab.fleetmanagement.vehicle.brandmodel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;


@RestController
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class BrandModelController {

    private BrandModelService brandModelService;

    public BrandModelController(BrandModelService brandModelService) {
        this.brandModelService = brandModelService;
    }

    @GetMapping("vehicle/brandmodel")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<BrandModelDTO> getBrandModels() {
        return brandModelService.getBrandsModels();
    }

    @GetMapping("vehicle/brandmodel/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public BrandModelDTO getBrandModel(@PathVariable Long id) {
        try {
            return brandModelService.getBrandModel(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("vehicle/brandmodel")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
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
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
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
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteBrandModel(@PathVariable Long id) {
        try {
            brandModelService.deleteBrandModel(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
