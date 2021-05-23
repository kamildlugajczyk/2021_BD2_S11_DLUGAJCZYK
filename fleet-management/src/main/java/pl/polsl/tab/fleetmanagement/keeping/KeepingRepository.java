package pl.polsl.tab.fleetmanagement.keeping;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.tab.fleetmanagement.vehicletype.TypesEntity;

public interface KeepingRepository extends CrudRepository<KeepingEntity, Long> {


}
