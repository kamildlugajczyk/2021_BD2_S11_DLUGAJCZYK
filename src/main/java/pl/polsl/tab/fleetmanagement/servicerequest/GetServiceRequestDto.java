package pl.polsl.tab.fleetmanagement.servicerequest;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.tab.fleetmanagement.person.PersonDTO;
import pl.polsl.tab.fleetmanagement.servicetype.ServiceTypeEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;

import java.util.Date;

@Getter @Setter
public class GetServiceRequestDto {
    private Long id;
    private Date date;
    private String description;
    private ServiceTypeEntity serviceType;
    private VehicleDTO vehicleDTO;
    private PersonDTO personDTO;
    private Boolean processed;

    private Long serviceTypesId;
    private Long vehiclesId;
    private Long personId;

    public GetServiceRequestDto(ServiceRequestEntity serviceRequestEntity, PersonDTO personDTO, VehicleDTO vehicleDTO, ServiceTypeEntity serviceTypeEntity) {
        this.id = serviceRequestEntity.getId();
        this.date = serviceRequestEntity.getDate();
        this.description = serviceRequestEntity.getDescription();
        this.serviceType = serviceTypeEntity;
        this.vehicleDTO = vehicleDTO;
        this.personDTO = personDTO;
        this.processed = serviceRequestEntity.getProcessed();

        this.serviceTypesId = serviceTypeEntity.getId();
        this.vehiclesId = vehicleDTO.getId();
        this.personId = personDTO.getId();
    }
}
