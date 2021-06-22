package pl.polsl.tab.fleetmanagement.servicerequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.tab.fleetmanagement.auth.JwtAuthenticationRequest;
import pl.polsl.tab.fleetmanagement.auth.UserPrincipal;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.person.PersonDTO;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;
import pl.polsl.tab.fleetmanagement.person.PersonService;
import pl.polsl.tab.fleetmanagement.servicetype.ServiceTypeEntity;
import pl.polsl.tab.fleetmanagement.servicetype.ServiceTypeRepository;
import pl.polsl.tab.fleetmanagement.servicing.ServicingService;
import pl.polsl.tab.fleetmanagement.servicing.ServicingDto;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundException;
import pl.polsl.tab.fleetmanagement.servicing.ServicingEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleRepository;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableTransactionManagement
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;
    private final VehicleService vehicleService;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServicingService servicingService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceRequestService (
            ServiceRequestRepository serviceRequestRepository,
            ServicingService servicingService,
            PersonRepository personRepository,
            VehicleService vehicleService,
            ServiceTypeRepository serviceTypeRepository,
            PersonService personService,
            ModelMapper modelMapper
    ) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.personService = personService;
        this.personRepository = personRepository;
        this.vehicleService = vehicleService;
        this.serviceTypeRepository = serviceTypeRepository;
        this.servicingService = servicingService;
        this.modelMapper = modelMapper;
    }

    private GetServiceRequestDto convertToDto(ServiceRequestEntity item) {
        VehicleDTO vehicleDTO = this.vehicleService.getVehicle(item.getVehiclesId());
        PersonDTO personDTO = this.personService.getPerson(item.getPeopleId());
        ServiceTypeEntity serviceTypeEntity = this.serviceTypeRepository.findById(item.getId())
                .orElseThrow(() -> new IdNotFoundException("Service Request", item.getId()));

        return new GetServiceRequestDto(item, personDTO, vehicleDTO, serviceTypeEntity);
    }

    private List<GetServiceRequestDto> convertToDtoList(List<ServiceRequestEntity> items) {
        List<GetServiceRequestDto> result = new LinkedList<>();

        for (var el : items) {
            GetServiceRequestDto item = this.convertToDto(el);
            result.add(item);
        }

        return result;
    }

    public List<GetServiceRequestDto> getAllServicesRequest() {
        var items = this.serviceRequestRepository.findAll();
        return convertToDtoList(items);
    }

    public List<GetServiceRequestDto> getAllUnprocessedServicesRequest() {
        List<GetServiceRequestDto> allItems = this.getAllServicesRequest();
        return allItems.stream().filter(i -> !i.getProcessed()).collect(Collectors.toList());
    }

    public List<GetServiceRequestDto> getUnprocessedServicesRequestPersonal() {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Long currentKeeperId = this.personRepository.findByUsername(username).getId();

        List<ServiceRequestEntity> response = new LinkedList<>();

        var allItems = this.serviceRequestRepository.findAll();

        var unprocessed = allItems
                .stream()
                .filter(i -> !i.getProcessed())
                .collect(Collectors.toList());

        for (ServiceRequestEntity serviceRequest: unprocessed) {
            var allKeepingData = serviceRequest.getVehiclesByVehiclesId().getKeepingsById();

            for (KeepingEntity keepingItem: allKeepingData) {
                if(keepingItem.getEnddate() != null) continue;

                if(keepingItem.getPeopleId() == currentKeeperId) {
                    response.add(serviceRequest);
                    break;
                }

            }
        }

        return this.convertToDtoList(response);

    }

    public List<GetServiceRequestDto> getAllProcessedServicesRequest() {
        List<GetServiceRequestDto> allItems = this.getAllServicesRequest();
        return allItems.stream().filter(GetServiceRequestDto::getProcessed).collect(Collectors.toList());
    }

    public GetServiceRequestDto getServiceRequestById(Long id) {

        ServiceRequestEntity item = this.serviceRequestRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundException("Service Request", id));

        return this.convertToDto(item);
    }

    public GetServiceRequestDto addServiceRequest(ServiceRequestDto requestDto) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Long userId = this.personRepository.findByUsername(username).getId();

        System.out.println(userId);

        ServiceRequestEntity sre = this.modelMapper.map(requestDto, ServiceRequestEntity.class);
        sre.setProcessed(false);
        sre.setPeopleId(userId);

        ServiceRequestEntity result = this.serviceRequestRepository.save(sre);

        return this.convertToDto(result);
    }

    /**
     * Method try processed service request. If no error request is set as processed and
     * servicing information is adding to database
     * */
    @Transactional
    public ServicingEntity executeServiceRequest(ServicingDto servicingDto, Long id) {
        try {
            ServiceRequestEntity sre = this.serviceRequestRepository
                    .findById(id)
                    .orElseThrow(() -> new IdNotFoundException("Service Request", id));

            if(sre.getProcessed())
                throw new RuntimeException("Service request already processed");

            // Add servicing to database
            ServicingEntity se = this.servicingService.addServicing(servicingDto, sre.getVehiclesId(), id);

            // Set request as processed
            sre.setProcessed(true);
            this.serviceRequestRepository.save(sre);

            return se;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteServiceRequest(Long id) {
        try {
            this.serviceRequestRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new IdNotFoundException("Service Request", id);
        }
    }
}
