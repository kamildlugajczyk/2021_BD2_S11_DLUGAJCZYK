package pl.polsl.tab.fleetmanagement.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.models.SubcontractorsEntity;
import pl.polsl.tab.fleetmanagement.repositories.SubcontractorsRepository;
import java.util.Optional;

@Component
public class SubcontractorsService {

    private final SubcontractorsRepository subcontractorsRepository;

    @Autowired
    public SubcontractorsService(SubcontractorsRepository subcontractorsRepository) {
        this.subcontractorsRepository = subcontractorsRepository;
    }

    public Iterable<SubcontractorsEntity> getSubcontractors() {
        return this.subcontractorsRepository.findAll();
    }

    public Optional<SubcontractorsEntity> getSubcontractorById(Long id) {
        return this.subcontractorsRepository.findById(id);
    }

    public SubcontractorsEntity addSubcontractor(SubcontractorsEntity subcontractor) {
        return subcontractorsRepository.save(new SubcontractorsEntity(
                subcontractor.getName(),
                subcontractor.getAddress(),
                subcontractor.getPhoneNumber()
        ));
    }

    public void deleteSubcontractorById(Long id) {
        this.subcontractorsRepository.deleteById(id);
    }
}
