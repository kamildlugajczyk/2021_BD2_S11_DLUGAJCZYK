package pl.polsl.tab.fleetmanagement.people;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.people.json.PersonPOST;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

    public void addPerson(PersonPOST newPersonData) {
        if (!this.personRepository.existsByUsername(newPersonData.getUsername())) {
            PersonEntity p = this.modelMapper.map(newPersonData, PersonEntity.class);
            String hash = this.passwordEncoder.encode(newPersonData.getPassword());
            p.setPassword(hash);
            System.out.println(p.toString());
            this.personRepository.save(p);
        } else {
            throw new RuntimeException("Username exists in database");
        }
    }
}
