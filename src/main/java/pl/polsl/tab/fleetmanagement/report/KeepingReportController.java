package pl.polsl.tab.fleetmanagement.report;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.tab.fleetmanagement.keeping.KeepingRepository;
import pl.polsl.tab.fleetmanagement.person.PersonService;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleService;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;


@RestController
@RequestMapping(path = "/keeping/report")
public class KeepingReportController {

    private final PersonService personService;
    private final VehicleService vehicleService;
    private final KeepingRepository keepingRepository;

    public KeepingReportController(PersonService personService, VehicleService vehicleService, KeepingRepository keepingRepository) {
        this.personService = personService;
        this.vehicleService = vehicleService;
        this.keepingRepository = keepingRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE, value = "/vehicle-keeper")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public void vehicleKeeperReport(HttpServletResponse response, @RequestParam(value="vehicleId") Set<Long> vehicleIds) {
        try {
            KeepingReportService keepingReportService = new KeepingReportService(personService, vehicleService, keepingRepository);
            keepingReportService.generateReport(response, vehicleIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}