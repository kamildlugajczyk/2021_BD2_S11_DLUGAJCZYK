package pl.polsl.tab.fleetmanagement.person;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/person")
    public List<PersonDTO> getAllPeople() {
        return personService.getAllPeople();
    }


}
