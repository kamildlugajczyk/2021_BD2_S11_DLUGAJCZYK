package pl.polsl.tab.fleetmanagement.person;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.function.FunctionEntity;
import pl.polsl.tab.fleetmanagement.function.FunctionRepository;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private FunctionRepository functionRepository;

    public PersonService(PersonRepository personRepository, FunctionRepository functionRepository) {
        this.personRepository = personRepository;
        this.functionRepository = functionRepository;
    }

    public List<PersonDTO> getAllPeople() {
        List<PersonEntity> peopleEntities = new ArrayList<>();
        List<PersonDTO> personDTOS = new ArrayList<>();

        personRepository.findAll().forEach(peopleEntities::add);

        for (PersonEntity personEntity : peopleEntities) {
            personDTOS.add(new PersonDTO(personEntity.getId(), personEntity.getFirstname(), personEntity.getLastname(),
                    personEntity.getPhoneNumber(), personEntity.getFunctionsByFunctionsId()/*, personEntity.getKeepingsById()*/));
        }

        return personDTOS;
    }

    public PersonDTO getPerson(Long id) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Person of id " + id + " not found"));

        return new PersonDTO(personEntity);
    }

    public PersonDTO addPerson(PersonDTORequest personDTO) {

        if(!this.validatePhoneNumber(personDTO.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        FunctionEntity functionEntity = functionRepository.findById(personDTO.getFunctionId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Function of id " + personDTO.getFunctionId() + " not found"));

        try {
            PersonEntity personEntity = personRepository.save(new PersonEntity(personDTO.getFirstname(),
                    personDTO.getLastname(), personDTO.getPhoneNumber(), functionEntity));
            return new PersonDTO(personEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO check if email exists (after database refactor)
                    //throw new ItemExistsInDatabaseException("Email ( " + personDTO.getEmail() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public PersonDTO updatePerson(Long id, PersonDTORequest personDTO) {
        Optional<PersonEntity> personEntity = personRepository.findById(id);

        if(personEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Person of id " + id + " not found");

        FunctionEntity functionEntity = functionRepository.findById(personDTO.getFunctionId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Function of id " + personDTO.getFunctionId() + " not found"));

        try {
            personEntity.get().setFirstname(personDTO.getFirstname());
            personEntity.get().setLastname(personDTO.getLastname());
            personEntity.get().setPhoneNumber(personDTO.getPhoneNumber());
            personEntity.get().setFunctionsId(functionEntity.getId());
            personEntity.get().setFunctionsByFunctionsId(functionEntity);
            return new PersonDTO(personRepository.save(personEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    // TODO check if email exists (after database refactor)
                    //throw new ItemExistsInDatabaseException("Vehicle type ( " + typeDTO.getName() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deletePerson(Long id) { ;
        try {
            personRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    public Iterable<KeepingDTO> getPersonsKeepings(Long id) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Person of id " + id + " not found"));

        List<KeepingDTO> keepingDTOs = new ArrayList<>();
        List<KeepingEntity> keepingEntities = new ArrayList<>();
        personEntity.getKeepingsById().forEach(keepingEntities::add);

        for (KeepingEntity keepingEntity : keepingEntities) {
            keepingDTOs.add(new KeepingDTO(keepingEntity));
        }

        return keepingDTOs;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        Pattern pattern = Pattern.compile(patterns);
        return pattern.matcher(phoneNumber).matches();
    }
}
