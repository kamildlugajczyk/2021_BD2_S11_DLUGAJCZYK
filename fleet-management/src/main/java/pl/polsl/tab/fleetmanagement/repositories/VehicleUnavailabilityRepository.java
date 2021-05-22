package pl.polsl.tab.fleetmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.tab.fleetmanagement.models.VehicleUnavailabilityEntity;

public interface VehicleUnavailabilityRepository extends JpaRepository<VehicleUnavailabilityEntity, Long> {
}
