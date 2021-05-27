package pl.polsl.tab.fleetmanagement.people;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PeopleController {

    private PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping("/people")
    public List<PeopleDTO> getAllPeople() {
        return peopleService.getAllPeople();
    }
}
