package pl.polsl.tab.fleetmanagement.vehicletype;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    private TypesRepository typesRepository;

    public TypesService(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }


    public Iterable<TypeDTO> getTypes() {
        List<TypesEntity> typesEntities = new ArrayList<>();
        List<TypeDTO> typesDTOs = new ArrayList<>();

        typesRepository.findAll().forEach(typesEntities::add);

        for (TypesEntity typesEntity : typesEntities) {
            typesDTOs.add(new TypeDTO(typesEntity.getId(), typesEntity.getName()));
        }

        return typesDTOs;
    }

    public Optional<TypeDTO> getType(Long id) {
        Optional<TypesEntity> typesEntity = typesRepository.findById(id);

        if (typesEntity.isPresent()) {
            TypeDTO typeDTO = new TypeDTO(typesEntity.get().getId(), typesEntity.get().getName());
            return Optional.of(typeDTO);
        }
        return Optional.empty();
    }

    public TypesEntity addType(TypeDTO typeDTO) {
        return typesRepository.save(new TypesEntity(typeDTO.getName()));
    }

    public Optional<TypeDTO> updateType(Long id, TypeDTO typeDTO) {
        Optional<TypesEntity> typesEntity = typesRepository.findById(id);

        if (typesEntity.isPresent()) {
            typesEntity.get().setName(typeDTO.getName());
            typesRepository.save(typesEntity.get());
            return Optional.of(new TypeDTO(typesEntity.get().getId(), typesEntity.get().getName()));
        }
        return Optional.empty();
    }

    public Optional<TypeDTO> deleteType(Long id) {
        Optional<TypesEntity> typesEntity = typesRepository.findById(id);

        if (typesEntity.isPresent()) {
            typesRepository.deleteById(id);
            return Optional.of(new TypeDTO(typesEntity.get().getId(), typesEntity.get().getName()));
        }
        return Optional.empty();
    }
}
