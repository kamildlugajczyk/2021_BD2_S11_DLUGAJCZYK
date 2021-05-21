package pl.polsl.tab.fleetmanagement.function;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FunctionsService {

    private FunctionsRepository functionsRepository;

    public FunctionsService(FunctionsRepository functionsRepository) {
        this.functionsRepository = functionsRepository;
    }


    public Iterable<FunctionDTO> getFunctions() {
        List<FunctionsEntity> functionsEntities = new ArrayList<>();
        List<FunctionDTO> functionDTOs = new ArrayList<>();

        functionsRepository.findAll().forEach(functionsEntities::add);

        for (FunctionsEntity functionsEntity : functionsEntities) {
            functionDTOs.add(new FunctionDTO(functionsEntity));
        }

        return functionDTOs;
    }

    public Optional<FunctionDTO> getFunction(Long id) {
        Optional<FunctionsEntity> functionsEntity = functionsRepository.findById(id);

        if (functionsEntity.isPresent()) {
            FunctionDTO functionDTO = new FunctionDTO(functionsEntity.get());
            return Optional.of(functionDTO);
        }
        return Optional.empty();
    }

    public FunctionsEntity addFunction(FunctionDTO functionDTO) {
        return functionsRepository.save(new FunctionsEntity(functionDTO.getName()));
    }

    public Optional<FunctionDTO> updateFunction(Long id, FunctionDTO functionDTO) {
        Optional<FunctionsEntity> functionsEntity = functionsRepository.findById(id);

        if (functionsEntity.isPresent()) {
            functionsEntity.get().setName(functionDTO.getName());
            functionsRepository.save(functionsEntity.get());
            return Optional.of(new FunctionDTO(functionsEntity.get()));
        }
        return Optional.empty();
    }

    public Optional<FunctionDTO> deleteFunction(Long id) {
        Optional<FunctionsEntity> functionsEntity = functionsRepository.findById(id);

        if (functionsEntity.isPresent()) {
            functionsRepository.deleteById(id);
            return Optional.of(new FunctionDTO(functionsEntity.get()));
        }
        return Optional.empty();
    }
}
