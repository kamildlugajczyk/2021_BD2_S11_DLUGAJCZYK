package pl.polsl.tab.fleetmanagement.servicetype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.exceptions.NotUniqueException;
import pl.polsl.tab.fleetmanagement.other.PostValue;

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

    public ServiceTypeEntity getServiceTypesById(Long id) throws IdNotFoundException {
        return this.serviceTypeRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundException("Service Type", id));
    }

    public ServiceTypeEntity addServiceTypes(PostValue<String> service) {
        try {
            return this.serviceTypeRepository.save(new ServiceTypeEntity(service.getName()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new NotUniqueException("Service Type", "name", service.getName());
                }
            }
            throw new RuntimeException(e);
        }
    }

    public ServiceTypeEntity updateServiceType(Long id, PostValue<String> service) {
        Optional<ServiceTypeEntity> ObjById = this.serviceTypeRepository.findById(id);

        if(ObjById.isEmpty()) throw new IdNotFoundException("Subcontractor", id);

        try {
            ObjById.get().setName(service.getName());
            return this.serviceTypeRepository.save(ObjById.get());
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new NotUniqueException("Service Type", "name", service.getName());
                }
            }
            throw new RuntimeException(e);
        }

    }

    public void deleteServiceTypeById(Long id) throws IdNotFoundException {
        try {
            this.serviceTypeRepository.deleteById(id);
        } catch (RuntimeException ignored) {
            throw new IdNotFoundException("Service Type", id);
        }
    }
}
