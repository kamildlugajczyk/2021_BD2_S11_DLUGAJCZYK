package pl.polsl.tab.fleetmanagement.rentings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRentingRepository extends JpaRepository<VehicleRentingEntity, Long> {
}
