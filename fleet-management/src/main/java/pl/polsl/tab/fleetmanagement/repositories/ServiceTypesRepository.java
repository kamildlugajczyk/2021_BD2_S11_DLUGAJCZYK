package pl.polsl.tab.fleetmanagement.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.tab.fleetmanagement.models.ServiceTypesEntity;

public interface ServiceTypesRepository extends CrudRepository<ServiceTypesEntity, Long> {
}
