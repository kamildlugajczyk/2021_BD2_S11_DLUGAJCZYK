package pl.polsl.tab.fleetmanagement.vehiclepurpose;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurposesService {

    private PurposesRepository purposesRepository;

    public PurposesService(PurposesRepository purposesRepository) {
        this.purposesRepository = purposesRepository;
    }


    public Iterable<PurposeDTO> getPurposes() {
        List<PurposesEntity> purposesEntities = new ArrayList<>();
        List<PurposeDTO> purposeDTOs = new ArrayList<>();

        purposesRepository.findAll().forEach(purposesEntities::add);

        for (PurposesEntity purposesEntity : purposesEntities) {
            purposeDTOs.add(new PurposeDTO(purposesEntity.getId(), purposesEntity.getName()));
        }

        return purposeDTOs;
    }

    public Optional<PurposeDTO> getPurpose(Long id) {
        Optional<PurposesEntity> purposesEntity = purposesRepository.findById(id);

        if (purposesEntity.isPresent()) {
            PurposeDTO purposeDTO = new PurposeDTO(purposesEntity.get().getId(), purposesEntity.get().getName());
            return Optional.of(purposeDTO);
        }
        return Optional.empty();
    }

    public PurposesEntity addPurpose(PurposeDTO purposeDTO) {
        return purposesRepository.save(new PurposesEntity(purposeDTO.getName()));
    }

    public Optional<PurposeDTO> updatePurpose(Long id, PurposeDTO purposeDTO) {
        Optional<PurposesEntity> purposesEntity = purposesRepository.findById(id);

        if (purposesEntity.isPresent()) {
            purposesEntity.get().setName(purposeDTO.getName());
            purposesRepository.save(purposesEntity.get());
            return Optional.of(new PurposeDTO(purposesEntity.get().getId(), purposesEntity.get().getName()));
        }
        return Optional.empty();
    }

    public Optional<PurposeDTO> deletePurpose(Long id) {
        Optional<PurposesEntity> purposesEntity = purposesRepository.findById(id);

        if (purposesEntity.isPresent()) {
            purposesRepository.deleteById(id);
            return Optional.of(new PurposeDTO(purposesEntity.get().getId(), purposesEntity.get().getName()));
        }
        return Optional.empty();
    }
}
