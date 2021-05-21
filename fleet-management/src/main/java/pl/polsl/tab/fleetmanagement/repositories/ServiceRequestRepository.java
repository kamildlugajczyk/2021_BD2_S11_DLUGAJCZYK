package pl.polsl.tab.fleetmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.tab.fleetmanagement.models.ServiceRequestEntity;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequestEntity, Long> {
}
