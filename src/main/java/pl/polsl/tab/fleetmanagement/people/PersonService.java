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
        List<PersonDTO> peopleDTOs = new ArrayList<>();

        personRepository.findAll().forEach(peopleEntities::add);

        for (PersonEntity peopleEntity : peopleEntities) {
            peopleDTOs.add(new PersonDTO(peopleEntity.getId(), peopleEntity.getFirstname(), peopleEntity.getLastname(),
                    peopleEntity.getPhonenumber(), peopleEntity.getFunctionsByFunctionsId(), peopleEntity.getKeepingsById()));
        }

        return peopleDTOs;
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
