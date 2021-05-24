package pl.polsl.tab.fleetmanagement.servicerequest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequestEntity, Long> {
}
