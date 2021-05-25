package pl.polsl.tab.fleetmanagement.person;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> getAllPeople() {
        List<PersonEntity> peopleEntities = new ArrayList<>();
        List<PersonDTO> personDTOS = new ArrayList<>();

        personRepository.findAll().forEach(peopleEntities::add);

        for (PersonEntity personEntity : peopleEntities) {
            personDTOS.add(new PersonDTO(personEntity.getId(), personEntity.getFirstname(), personEntity.getLastname(),
                    personEntity.getPhonenumber(), personEntity.getFunctionsByFunctionsId(), personEntity.getKeepingsById()));
        }

        return personDTOS;
    }
}
