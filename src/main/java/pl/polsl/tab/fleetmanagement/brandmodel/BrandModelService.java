package pl.polsl.tab.fleetmanagement.brandmodel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandModelService {

    private BrandModelRepository brandModelRepository;

    public BrandModelService(BrandModelRepository brandModelRepository) {
        this.brandModelRepository = brandModelRepository;
    }


    public Iterable<BrandModelDTO> getBrandsModels() {
        List<BrandModelEntity> brandModelEntities = new ArrayList<>();
        List<BrandModelDTO> brandModelDTOs = new ArrayList<>();

        brandModelRepository.findAll().forEach(brandModelEntities::add);

        for (BrandModelEntity brandModelEntity : brandModelEntities) {
            brandModelDTOs.add(new BrandModelDTO(brandModelEntity));
        }

        return brandModelDTOs;
    }

    public BrandModelDTO getBrandModel(Long id) {
        BrandModelEntity brandModelEntity = brandModelRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle type " + id + " not found"));

        return new BrandModelDTO(brandModelEntity);
    }

    public BrandModelDTO addBrandModel(BrandModelDTO brandModelDTO) {
        try {
            BrandModelEntity brandModelEntity = brandModelRepository.save(new BrandModelEntity(brandModelDTO.getBrand(),
                    brandModelDTO.getModel(), brandModelDTO.getModelYear()));
            return new BrandModelDTO(brandModelEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Brand, model and model year ( " + brandModelDTO.getBrand()
                            + ", " + brandModelDTO.getModel() + ", " + brandModelDTO.getModelYear() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public BrandModelDTO updateBrandModel(Long id, BrandModelDTO brandModelDTO) {
        Optional<BrandModelEntity> brandModelEntity = brandModelRepository.findById(id);

        if(brandModelEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Vehicle type of id " + id + " not found");

        try {
            brandModelEntity.get().setBrand(brandModelDTO.getBrand());
            brandModelEntity.get().setModel(brandModelDTO.getModel());
            brandModelEntity.get().setModelYear(brandModelDTO.getModelYear());
            return new BrandModelDTO(brandModelRepository.save(brandModelEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Brand, model and model year ( " + brandModelDTO.getBrand()
                            + ", " + brandModelDTO.getModel() + ", " + brandModelDTO.getModelYear() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteBrandModel(Long id) {
        try {
            brandModelRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
