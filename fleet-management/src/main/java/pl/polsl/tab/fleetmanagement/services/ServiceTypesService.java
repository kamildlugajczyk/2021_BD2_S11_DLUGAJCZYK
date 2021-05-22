package pl.polsl.tab.fleetmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServiceTypesEntity;
import pl.polsl.tab.fleetmanagement.repositories.ServiceTypesRepository;

import java.sql.SQLException;
import java.util.Optional;

@Component
public class ServiceTypesService {

    private final ServiceTypesRepository serviceTypesRepository;

    @Autowired
    public ServiceTypesService(ServiceTypesRepository serviceTypesRepository) {
        this.serviceTypesRepository = serviceTypesRepository;
    }

    public Iterable<ServiceTypesEntity> getServiceTypes() {
        return this.serviceTypesRepository.findAll();
    }

    public ServiceTypesEntity getServiceTypesById(Long id) throws IdNotFoundInDatabaseException {
        return this.serviceTypesRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Service " + id + " not found"));
    }

    public ServiceTypesEntity addServiceTypes(String name) {
        try {
            return this.serviceTypesRepository.save(new ServiceTypesEntity(name));
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

    public ServiceTypesEntity updateServiceType(Long id, String name) {
        Optional<ServiceTypesEntity> ObjById = this.serviceTypesRepository.findById(id);

        if(ObjById.isEmpty()) throw new IdNotFoundInDatabaseException("Subcontractor " + id + " not found");

        try {
            ObjById.get().setName(name);
            return this.serviceTypesRepository.save(ObjById.get());
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
            this.serviceTypesRepository.deleteById(id);
        } catch (RuntimeException ignored) {
            throw new IdNotFoundInDatabaseException("Service " + id + " not found");
        }
    }
}
