package pl.polsl.tab.fleetmanagement.servicetype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public Iterable<ServiceTypeEntity> getServiceTypes() {
        return this.serviceTypeRepository.findAll();
    }

    public ServiceTypeEntity getServiceTypesById(Long id) throws IdNotFoundInDatabaseException {
        return this.serviceTypeRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Service " + id + " not found"));
    }

    public ServiceTypeEntity addServiceTypes(String name) {
        try {
            return this.serviceTypeRepository.save(new ServiceTypeEntity(name));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Service (" + name + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public ServiceTypeEntity updateServiceType(Long id, String name) {
        Optional<ServiceTypeEntity> ObjById = this.serviceTypeRepository.findById(id);

        if(ObjById.isEmpty()) throw new IdNotFoundInDatabaseException("Subcontractor " + id + " not found");

        try {
            ObjById.get().setName(name);
            return this.serviceTypeRepository.save(ObjById.get());
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Service ( " + name + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }

    }

    public void deleteServiceTypeById(Long id) throws IdNotFoundInDatabaseException {
        try {
            this.serviceTypeRepository.deleteById(id);
        } catch (RuntimeException ignored) {
            throw new IdNotFoundInDatabaseException("Service " + id + " not found");
        }
    }
}
