package pl.polsl.tab.fleetmanagement.subcontractor;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class SubcontractorController {

    private final SubcontractorService subcontractorService;

    @Autowired
    public SubcontractorController(SubcontractorService subcontractorService) {
        this.subcontractorService = subcontractorService;
    }

    @GetMapping("service/subcontractor/")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<SubcontractorEntity> getSubcontractors() {
        return this.subcontractorService.getSubcontractors();
    }

    @GetMapping("service/subcontractor/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public SubcontractorEntity getSubcontractorById(@PathVariable Long id) {
        return this.subcontractorService.getSubcontractorById(id);
    }

    @PostMapping("service/subcontractor/")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public SubcontractorEntity addSubcontractor(@RequestBody SubcontractorDto subcontractor) {
        return this.subcontractorService.addSubcontractor(subcontractor);
    }

    @PutMapping("service/subcontractor/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public SubcontractorEntity updateSubcontractorById(@PathVariable Long id, @RequestBody SubcontractorDto subcontractor) {
        return this.subcontractorService.updateSubcontractorById(id, subcontractor);
    }

    @DeleteMapping("service/subcontractor/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deleteSubcontractorById(@PathVariable Long id) {
        this.subcontractorService.deleteSubcontractorById(id);
    }
}
