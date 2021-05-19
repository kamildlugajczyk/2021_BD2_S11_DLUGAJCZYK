package pl.polsl.tab.fleetmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.SubcontractorsEntity;
import pl.polsl.tab.fleetmanagement.repositories.SubcontractorsRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

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

    public SubcontractorsEntity getSubcontractorById(Long id) throws IdNotFoundInDatabaseException {
        return this.subcontractorsRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Subcontractor " + id + " not found"));
    }

    public SubcontractorsEntity addSubcontractor(SubcontractorsEntity subcontractor) {

        if(!this.validatePhoneNumber(subcontractor.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        try {
            return subcontractorsRepository.save(
                new SubcontractorsEntity(
                    subcontractor.getName(),
                    subcontractor.getAddress(),
                    subcontractor.getPhoneNumber()
                )
            );
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Subcontractor ( " + subcontractor.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public SubcontractorsEntity updateSubcontractorById(SubcontractorsEntity subcontractor, Long id) {

        Optional<SubcontractorsEntity> ObjById = this.subcontractorsRepository.findById(id);

        if(ObjById.isEmpty()) throw new IdNotFoundInDatabaseException("Subcontractor " + id + " not found");

        if(!this.validatePhoneNumber(subcontractor.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        try {
            ObjById.get().setName(subcontractor.getName());
            ObjById.get().setAddress(subcontractor.getAddress());
            ObjById.get().setPhoneNumber(subcontractor.getPhoneNumber());
            return this.subcontractorsRepository.save(ObjById.get());
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Subcontractor ( " + subcontractor.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }

    }

    public void deleteSubcontractorById(Long id) throws IdNotFoundInDatabaseException {
        try {
            this.subcontractorsRepository.deleteById(id);
        } catch (RuntimeException ignored) {
            throw new IdNotFoundInDatabaseException("Subcontractor " + id + " not found");
        }

    }

    private boolean validatePhoneNumber(String phoneNumber) {
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        Pattern pattern = Pattern.compile(patterns);
        return pattern.matcher(phoneNumber).matches();
    }

}
