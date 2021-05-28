package pl.polsl.tab.fleetmanagement.people;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.people.json.PersonPOST;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping()
    public List<PersonDTO> getAllPeople() {
        return personService.getAllPeople();
    }

    @PostMapping()
    public void addPerson(@RequestBody() PersonPOST newPersonData) {
        try {
            this.personService.addPerson(newPersonData);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
