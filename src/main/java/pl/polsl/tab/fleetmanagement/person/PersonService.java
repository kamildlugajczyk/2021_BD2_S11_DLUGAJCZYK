package pl.polsl.tab.fleetmanagement.person;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.auth.ChangePasswordRequest;
import pl.polsl.tab.fleetmanagement.auth.UserPrincipal;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.WrongPasswordException;
import pl.polsl.tab.fleetmanagement.person.function.FunctionEntity;
import pl.polsl.tab.fleetmanagement.person.function.FunctionRepository;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private FunctionRepository functionRepository;
    private VehicleRepository vehicleRepository;

    public PersonService(PersonRepository personRepository, ModelMapper modelMapper,
                         PasswordEncoder passwordEncoder, FunctionRepository functionRepository,
                         VehicleRepository vehicleRepository) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.functionRepository = functionRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<PersonDTO> getAllPeople() {
        List<PersonEntity> peopleEntities = new ArrayList<>();
        List<PersonDTO> personDTOS = new ArrayList<>();

        personRepository.findAll().forEach(peopleEntities::add);

        for (PersonEntity personEntity : peopleEntities) {
            personDTOS.add(new PersonDTO(personEntity.getId(), personEntity.getUsername(), personEntity.getFirstname(), personEntity.getLastname(),
                    personEntity.getPhoneNumber(), personEntity.getFunctionsByFunctionsId()));
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
            String hash = this.passwordEncoder.encode(personDTO.getPassword());

            PersonEntity personEntity = personRepository.save(new PersonEntity(personDTO.getUsername(), hash,
                    personDTO.getFirstname(), personDTO.getLastname(), personDTO.getPhoneNumber(), functionEntity));
            return new PersonDTO(personEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Username (" + personDTO.getUsername() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public PersonDTO updatePerson(Long id, PersonDTORequest personDTO) {
        Optional<PersonEntity> personEntity = personRepository.findById(id);

        if(personEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Person of id " + id + " not found");

        if(!this.validatePhoneNumber(personDTO.getPhoneNumber()))
            throw new IllegalArgumentException("Invalid phone number format");

        FunctionEntity functionEntity = functionRepository.findById(personDTO.getFunctionId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Function of id " + personDTO.getFunctionId() + " not found"));

        try {
            personEntity.get().setUsername(personDTO.getUsername());
            personEntity.get().setFirstname(personDTO.getFirstname());
            personEntity.get().setLastname(personDTO.getLastname());
            personEntity.get().setPhoneNumber(personDTO.getPhoneNumber());
            personEntity.get().setFunctionId(functionEntity.getId());
            personEntity.get().setFunctionsByFunctionsId(functionEntity);
            return new PersonDTO(personRepository.save(personEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Username (" + personDTO.getUsername() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void changePassword(ChangePasswordRequest request) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PersonEntity personEntity = personRepository.findByUsername(userPrincipal.getUsername());

        String oldHash = this.passwordEncoder.encode(request.getOldPassword());

        if (BCrypt.checkpw(request.getOldPassword(), personEntity.getPassword())) {
            String newHash = this.passwordEncoder.encode(request.getNewPassword());
            personEntity.setPassword(newHash);
            personRepository.save(personEntity);
        } else throw new WrongPasswordException("Old password does not match!");
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
