//package pl.polsl.tab.fleetmanagement.keeping;
//
//import org.springframework.stereotype.Service;
//import pl.polsl.tab.fleetmanagement.vehicletype.TypeDTO;
//import pl.polsl.tab.fleetmanagement.vehicletype.TypesEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class KeepingService {
//
//    private KeepingRepository keepingRepository;
//
//    public KeepingService(KeepingRepository keepingRepository) {
//        this.keepingRepository = keepingRepository;
//    }
//
//
//    public Iterable<KeepingDTO> getTypes() {
//        List<KeepingEntity> keepingEntities = new ArrayList<>();
//        List<KeepingDTO> keepingDTOs = new ArrayList<>();
//
//        keepingRepository.findAll().forEach(keepingEntities::add);
//
//        for (KeepingEntity keepingEntity : keepingEntities) {
//            keepingDTOs.add(new KeepingDTO(keepingEntity));
//        }
//
//        return keepingDTOs;
//    }
//
//    public Optional<TypeDTO> getType(Long id) {
//        Optional<TypesEntity> typesEntity = keepingRepository.findById(id);
//
//        if (typesEntity.isPresent()) {
//            TypeDTO typeDTO = new TypeDTO(typesEntity.get());
//            return Optional.of(typeDTO);
//        }
//        return Optional.empty();
//    }
//
//    public TypesEntity addType(TypeDTO typeDTO) {
//        return keepingRepository.save(new TypesEntity(typeDTO.getName()));
//    }
//
//    public Optional<TypeDTO> updateType(Long id, TypeDTO typeDTO) {
//        Optional<TypesEntity> typesEntity = keepingRepository.findById(id);
//
//        if (typesEntity.isPresent()) {
//            typesEntity.get().setName(typeDTO.getName());
//            keepingRepository.save(typesEntity.get());
//            return Optional.of(new TypeDTO(typesEntity.get()));
//        }
//        return Optional.empty();
//    }
//
//    public Optional<TypeDTO> deleteType(Long id) {
//        Optional<TypesEntity> typesEntity = keepingRepository.findById(id);
//
//        if (typesEntity.isPresent()) {
//            keepingRepository.deleteById(id);
//            return Optional.of(new TypeDTO(typesEntity.get()));
//        }
//        return Optional.empty();
//    }
//}
