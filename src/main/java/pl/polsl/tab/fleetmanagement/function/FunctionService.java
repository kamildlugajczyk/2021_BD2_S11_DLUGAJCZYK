package pl.polsl.tab.fleetmanagement.function;

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
public class FunctionService {

    private FunctionRepository functionRepository;

    public FunctionService(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }


    public Iterable<FunctionDTO> getFunctions() {
        List<FunctionEntity> functionEntities = new ArrayList<>();
        List<FunctionDTO> functionDTOs = new ArrayList<>();

        functionRepository.findAll().forEach(functionEntities::add);

        for (FunctionEntity functionEntity : functionEntities) {
            functionDTOs.add(new FunctionDTO(functionEntity));
        }

        return functionDTOs;
    }

    public FunctionDTO getFunction(Long id) {
        FunctionEntity functionEntity = functionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Person function of " + id + " not found"));

        return new FunctionDTO(functionEntity);
    }

    public FunctionDTO addFunction(FunctionDTO functionDTO) {
        try {
            FunctionEntity functionEntity = functionRepository.save(new FunctionEntity(functionDTO.getName()));
            return new FunctionDTO(functionEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Person function ( " + functionDTO.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public FunctionDTO updateFunction(Long id, FunctionDTO functionDTO) {
        Optional<FunctionEntity> functionEntity = functionRepository.findById(id);

        if(functionEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Person function of " + id + " not found");

        try {
            functionEntity.get().setName(functionDTO.getName());
            return new FunctionDTO(functionRepository.save(functionEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Person function ( " + functionDTO.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteFunction(Long id) {
        try {
            functionRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
