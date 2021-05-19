package pl.polsl.tab.fleetmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.models.ServiceTypesEntity;
import pl.polsl.tab.fleetmanagement.repositories.ServiceTypesRepository;

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

    public Optional<ServiceTypesEntity> getServiceTypesById(Long id) {
        return this.serviceTypesRepository.findById(id);
    }

    public ServiceTypesEntity addServiceTypes(ServiceTypesEntity serviceTypesEntity) {
        return this.serviceTypesRepository.save(new ServiceTypesEntity(serviceTypesEntity.getName()));
    }

    public void deleteServiceTypeById(Long id) {
        this.serviceTypesRepository.deleteById(id);
    }
}
