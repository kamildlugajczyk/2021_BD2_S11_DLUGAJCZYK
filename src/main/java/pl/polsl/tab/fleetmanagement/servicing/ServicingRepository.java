package pl.polsl.tab.fleetmanagement.servicing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicingRepository extends JpaRepository<ServicingEntity, Long> {
    Optional<ServicingEntity> findByVehicleUnavailabilityId(Long id);
}
