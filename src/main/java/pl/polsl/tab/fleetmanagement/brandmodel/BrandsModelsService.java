package pl.polsl.tab.fleetmanagement.brandmodel;

import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.vehicletype.TypesEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandsModelsService {

    private BrandsModelsRepository brandsModelsRepository;

    public BrandsModelsService(BrandsModelsRepository brandsModelsRepository) {
        this.brandsModelsRepository = brandsModelsRepository;
    }


    public Iterable<BrandModelDTO> getBrandsModels() {
        List<BrandsModelsEntity> brandsModelsEntities = new ArrayList<>();
        List<BrandModelDTO> brandModelDTOs = new ArrayList<>();

        brandsModelsRepository.findAll().forEach(brandsModelsEntities::add);

        for (BrandsModelsEntity brandsModelsEntity : brandsModelsEntities) {
            brandModelDTOs.add(new BrandModelDTO(brandsModelsEntity.getId(), brandsModelsEntity.getBrand(),
                    brandsModelsEntity.getModel(), brandsModelsEntity.getModelYear()));
        }

        return brandModelDTOs;
    }

    public Optional<BrandModelDTO> getBrandModel(Long id) {
        Optional<BrandsModelsEntity> brandsModelsEntity = brandsModelsRepository.findById(id);

        if (brandsModelsEntity.isPresent()) {
            BrandModelDTO brandModelDTO = new BrandModelDTO(brandsModelsEntity.get().getId(), brandsModelsEntity.get().getBrand(),
                    brandsModelsEntity.get().getModel(), brandsModelsEntity.get().getModelYear());
            return Optional.of(brandModelDTO);
        }
        return Optional.empty();
    }

    public BrandsModelsEntity addBrandModel(BrandModelDTO brandModelDTO) {
        return brandsModelsRepository.save(new BrandsModelsEntity(brandModelDTO.getBrand(), brandModelDTO.getModel(),
                brandModelDTO.getModelYear()));
    }

    public Optional<BrandModelDTO> updateBrandModel(Long id, BrandModelDTO brandModelDTO) {
        Optional<BrandsModelsEntity> brandsModelsEntity = brandsModelsRepository.findById(id);

        if (brandsModelsEntity.isPresent()) {
            brandsModelsEntity.get().setBrand(brandModelDTO.getBrand());
            brandsModelsEntity.get().setModel(brandModelDTO.getModel());
            brandsModelsEntity.get().setModelYear(brandModelDTO.getModelYear());
            brandsModelsRepository.save(brandsModelsEntity.get());
            return Optional.of(new BrandModelDTO(brandsModelsEntity.get().getId(), brandsModelsEntity.get().getBrand(),
                    brandsModelsEntity.get().getModel(), brandsModelsEntity.get().getModelYear()));
        }
        return Optional.empty();
    }

    public Optional<BrandModelDTO> deleteBrandModel(Long id) {
        Optional<BrandsModelsEntity> brandsModelsEntity = brandsModelsRepository.findById(id);

        if (brandsModelsEntity.isPresent()) {
            brandsModelsRepository.deleteById(id);
            return Optional.of(new BrandModelDTO(brandsModelsEntity.get().getId(), brandsModelsEntity.get().getBrand(),
                    brandsModelsEntity.get().getModel(), brandsModelsEntity.get().getModelYear()));
        }
        return Optional.empty();
    }
}
