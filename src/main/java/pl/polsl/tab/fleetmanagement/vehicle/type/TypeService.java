package pl.polsl.tab.fleetmanagement.vehicle.type;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.other.PostValue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    private TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    public Iterable<TypeDTO> getTypes() {
        List<TypeEntity> typeEntities = new ArrayList<>();
        List<TypeDTO> typeDTOs = new ArrayList<>();

        typeRepository.findAll().forEach(typeEntities::add);

        for (TypeEntity typeEntity : typeEntities) {
            typeDTOs.add(new TypeDTO(typeEntity));
        }

        return typeDTOs;
    }

    public TypeDTO getType(Long id) {
        TypeEntity typeEntity = typeRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle type of id " + id + " not found"));

        return new TypeDTO(typeEntity);
    }

    public TypeDTO addType(PostValue<String> type) {
        try {
            TypeEntity typeEntity = typeRepository.save(new TypeEntity(type.getName()));
            return new TypeDTO(typeEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Vehicle type ( " + type.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public TypeDTO updateType(Long id, PostValue<String> type) {
        Optional<TypeEntity> typeEntity = typeRepository.findById(id);

        if(typeEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Vehicle type of id " + id + " not found");

        try {
            typeEntity.get().setName(type.getName());
            return new TypeDTO(typeRepository.save(typeEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Vehicle type ( " + type.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteType(Long id) { ;
        try {
            typeRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
