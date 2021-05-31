package pl.polsl.tab.fleetmanagement.vehicle.purpose;

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
public class PurposeService {

    private PurposeRepository purposeRepository;

    public PurposeService(PurposeRepository purposeRepository) {
        this.purposeRepository = purposeRepository;
    }


    public Iterable<PurposeDTO> getPurposes() {
        List<PurposeEntity> purposeEntities = new ArrayList<>();
        List<PurposeDTO> purposeDTOs = new ArrayList<>();

        purposeRepository.findAll().forEach(purposeEntities::add);

        for (PurposeEntity purposeEntity : purposeEntities) {
            purposeDTOs.add(new PurposeDTO(purposeEntity));
        }

        return purposeDTOs;
    }

    public PurposeDTO getPurpose(Long id) {
        PurposeEntity purposeEntity = purposeRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle purpose of id " + id + " not found"));

        return new PurposeDTO(purposeEntity);
    }

    public PurposeDTO addPurpose(PurposeDTO purposeDTO) {
        try {
            PurposeEntity purposeEntity = purposeRepository.save(new PurposeEntity(purposeDTO.getName()));
            return new PurposeDTO(purposeEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Vehicle purpose ( " + purposeDTO.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public PurposeDTO updatePurpose(Long id, PurposeDTO purposeDTO) {
        Optional<PurposeEntity> purposeEntity = purposeRepository.findById(id);

        if(purposeEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Vehicle purpose of id " + id + " not found");

        try {
            purposeEntity.get().setName(purposeDTO.getName());
            return new PurposeDTO(purposeRepository.save(purposeEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Vehicle purpose ( " + purposeDTO.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deletePurpose(Long id) {
        try {
            purposeRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
