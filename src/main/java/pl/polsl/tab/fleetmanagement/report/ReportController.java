package pl.polsl.tab.fleetmanagement.report;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.tab.fleetmanagement.exploitation.OperationCostService;
import pl.polsl.tab.fleetmanagement.exploitation.type.OperationTypeService;
import pl.polsl.tab.fleetmanagement.person.PersonService;
import pl.polsl.tab.fleetmanagement.rentings.VehicleRentingService;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;
import pl.polsl.tab.fleetmanagement.vehicleunavailability.VehicleUnavailabilityService;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path = "/report")
public class ReportController {

    private final PersonService personService;
    private final VehicleRentingService vehicleRentingService;
    private final VehicleUnavailabilityService vehicleUnavailabilityService;
    private final VehicleService vehicleService;
    private final OperationCostService operationCostService;
    private final OperationTypeService operationTypeService;

    public ReportController(PersonService personService, VehicleRentingService vehicleRentingService,
                            VehicleUnavailabilityService vehicleUnavailabilityService, VehicleService vehicleService,
                            OperationCostService operationCostService, OperationTypeService operationTypeService) {
        this.personService = personService;
        this.vehicleRentingService = vehicleRentingService;
        this.vehicleUnavailabilityService = vehicleUnavailabilityService;
        this.vehicleService = vehicleService;
        this.operationCostService = operationCostService;
        this.operationTypeService = operationTypeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public void getPdf(HttpServletResponse response) {
        try {
            ReportService reportService = new ReportService(personService, vehicleRentingService,
                    vehicleUnavailabilityService, vehicleService, operationCostService, operationTypeService);
            reportService.generateReport(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
