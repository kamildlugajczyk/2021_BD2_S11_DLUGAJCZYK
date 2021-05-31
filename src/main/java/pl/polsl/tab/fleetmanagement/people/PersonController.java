package pl.polsl.tab.fleetmanagement.people;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.*;
import pl.polsl.tab.fleetmanagement.people.json.PersonPOST;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<PersonDTO> getAllPeople() {
        return personService.getAllPeople();
    }

    @PostMapping()
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void addPerson(@RequestBody() PersonPOST newPersonData) {
        this.personService.addPerson(newPersonData);
    }

}
