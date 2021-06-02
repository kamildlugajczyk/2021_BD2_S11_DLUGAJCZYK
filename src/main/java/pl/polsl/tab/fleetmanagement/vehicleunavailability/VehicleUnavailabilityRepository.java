package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface VehicleUnavailabilityRepository extends CrudRepository<VehicleUnavailabilityEntity, Long> {
    Collection<? extends VehicleUnavailabilityEntity> findAllByPeopleId(Long id);
}
