package pl.polsl.tab.fleetmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.dto.SubcontractorsDto;
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
    private final ModelMapper modelMapper;

    @Autowired
    public SubcontractorsService(SubcontractorsRepository subcontractorsRepository, ModelMapper modelMapper) {
        this.subcontractorsRepository = subcontractorsRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<SubcontractorsEntity> getSubcontractors() {
        return this.subcontractorsRepository.findAll();
    }

    public SubcontractorsEntity getSubcontractorById(Long id) throws IdNotFoundInDatabaseException {
        return this.subcontractorsRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Subcontractor " + id + " not found"));
    }

    public SubcontractorsEntity addSubcontractor(SubcontractorsDto dto) {

        if(!this.validatePhoneNumber(dto.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        try {
            SubcontractorsEntity se = this.modelMapper.map(dto, SubcontractorsEntity.class);
            return subcontractorsRepository.save(se);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Subcontractor ( " + dto.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public SubcontractorsEntity updateSubcontractorById(Long id, SubcontractorsDto dto) {

        Optional<SubcontractorsEntity> se = this.subcontractorsRepository.findById(id);

        if(se.isEmpty()) throw new IdNotFoundInDatabaseException("Subcontractor " + id + " not found");

        if(!this.validatePhoneNumber(dto.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        try {
            se.get().setName(dto.getName());
            se.get().setAddress(dto.getAddress());
            se.get().setPhoneNumber(dto.getPhoneNumber());
            return this.subcontractorsRepository.save(se.get());
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Subcontractor ( " + dto.getName() + ") exists in DB");
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
