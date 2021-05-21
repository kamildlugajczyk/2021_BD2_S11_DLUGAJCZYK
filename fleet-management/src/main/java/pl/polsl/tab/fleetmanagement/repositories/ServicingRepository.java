package pl.polsl.tab.fleetmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;

public interface ServicingRepository extends JpaRepository<ServicingEntity, Long> {
}
