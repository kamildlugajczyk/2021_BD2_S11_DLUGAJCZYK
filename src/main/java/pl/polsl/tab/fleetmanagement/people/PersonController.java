package pl.polsl.tab.fleetmanagement.people;

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
    public List<PersonDTO> getAllPeople() {
        return personService.getAllPeople();
    }

    @PostMapping()
    public void addPerson(@RequestBody() PersonPOST newPersonData) {
        this.personService.addPerson(newPersonData);
    }

}
