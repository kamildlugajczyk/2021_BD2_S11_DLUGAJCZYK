package pl.polsl.tab.fleetmanagement.vehicleunavailability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface VehicleUnavailabilityRepository extends JpaRepository<VehicleUnavailabilityEntity, Long> {
    Collection<? extends VehicleUnavailabilityEntity> findAllByPeopleId(Long id);
}
