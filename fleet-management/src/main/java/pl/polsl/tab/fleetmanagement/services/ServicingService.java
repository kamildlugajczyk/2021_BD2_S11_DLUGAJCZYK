package pl.polsl.tab.fleetmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.models.ServicingEntity;
import pl.polsl.tab.fleetmanagement.repositories.ServicingRepository;

import java.util.List;

// TODO permissions

@Component
public class ServicingService {

    private final ServicingRepository servicingRepository;

    @Autowired
    public ServicingService (ServicingRepository servicingRepository) {
        this.servicingRepository = servicingRepository;
    }

    public List<ServicingEntity> getAllServicing() {
        return this.servicingRepository.findAll();
    }

    public ServicingEntity getServicingById(Long id) {
        return this.servicingRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Servicing " + id + " not exists"));
    }

    /**
     * @warning Method not validate service request id
     * */
    public ServicingEntity addServicing(ServicingEntity servicing, Long serviceRequestId) {
        // TODO check vehicle unavailability (is vehicle available, date tdlr...)
        // TODO if everything is ok, generate unavailability and return ID
        // servicing.setServiceRequestId(serviceRequestId);
        return this.servicingRepository.save(servicing);
    }

    public ServicingEntity finishServicing(Long id) {
        // TODO set vehicle as available, update endDate
        ServicingEntity service = this.getServicingById(id);
        // service.setFinished(true);
        // TODO discuss delete from vehicle_unavailability
        return this.servicingRepository.save(service);
    }

    public void deleteServicing(Long id) {
        try {
            this.servicingRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new IdNotFoundInDatabaseException("Servicing " + id + " not exists");
        }
    }

}
