package pl.polsl.tab.fleetmanagement.servicing;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("service")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class ServicingController {

    private final ServicingService servicingService;

    @Autowired
    public ServicingController(ServicingService servicingService) {
        this.servicingService = servicingService;
    }

    @GetMapping("")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getAllServicing() {
        return this.servicingService.getAllServicing();
    }

    @GetMapping("finished")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getAllFinishedServicing() {
        return this.servicingService.getAllFinishedServicing();
    }

    @GetMapping("unfinished")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getAllUnfinishedServicing() {
        return this.servicingService.getAllUnfinishedServicing();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServicingEntity getServicingById(@PathVariable Long id) {
        return this.servicingService.getServicingById(id);
    }

    @GetMapping("keeper/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getServicingByKeeperId(@PathVariable Long id) {
        return this.servicingService.getServicingByKeeperId(id);
    }

    @GetMapping("keeper/{id}/unfinished")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getUnfinishedServicingByKeeperId(@PathVariable Long id) {
        return this.servicingService.getUnfinishedServicingByKeeperId(id);
    }

    @GetMapping("vehicle/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getServicingByVehicleId(@PathVariable Long id) {
        return this.servicingService.getServicingByVehicleId(id);
    }

    @GetMapping("vehicle/{id}/unfinished")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<ServicingEntity> getUnfinishedServicingByVehicleId(@PathVariable Long id) {
        return this.servicingService.getUnfinishedServicingByVehicleId(id);
    }

    @PostMapping("/keeper")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServicingEntity addServicing(
            @RequestBody ServicingDto request,
            @RequestParam(name="keeperId") Long keeperId,
            @RequestParam(name="vehicleId") Long vehicleId) {
        return this.servicingService.addServicing(request, keeperId, vehicleId, null);
    }

    @PatchMapping("/keeper/{id}/finish")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ServicingEntity finishServicing(@PathVariable Long id) {
        return this.servicingService.finishServicing(id);
    }

    @DeleteMapping("/keeper/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteServicing(@PathVariable Long id) {
        this.servicingService.deleteServicing(id);
    }
}
