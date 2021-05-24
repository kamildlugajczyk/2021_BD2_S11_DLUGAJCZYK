package pl.polsl.tab.fleetmanagement.subcontractor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class SubcontractorService {

    private final SubcontractorRepository subcontractorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubcontractorService(SubcontractorRepository subcontractorRepository, ModelMapper modelMapper) {
        this.subcontractorRepository = subcontractorRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<SubcontractorEntity> getSubcontractors() {
        return this.subcontractorRepository.findAll();
    }

    public SubcontractorEntity getSubcontractorById(Long id) throws IdNotFoundInDatabaseException {
        return this.subcontractorRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Subcontractor " + id + " not found"));
    }

    public SubcontractorEntity addSubcontractor(SubcontractorDto dto) {

        if(!this.validatePhoneNumber(dto.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        try {
            SubcontractorEntity se = this.modelMapper.map(dto, SubcontractorEntity.class);
            return subcontractorRepository.save(se);
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

    public SubcontractorEntity updateSubcontractorById(Long id, SubcontractorDto dto) {

        Optional<SubcontractorEntity> se = this.subcontractorRepository.findById(id);

        if(se.isEmpty()) throw new IdNotFoundInDatabaseException("Subcontractor " + id + " not found");

        if(!this.validatePhoneNumber(dto.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        try {
            se.get().setName(dto.getName());
            se.get().setAddress(dto.getAddress());
            se.get().setPhoneNumber(dto.getPhoneNumber());
            return this.subcontractorRepository.save(se.get());
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
            this.subcontractorRepository.deleteById(id);
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
