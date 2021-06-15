package pl.polsl.tab.fleetmanagement.person;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.WrongPasswordException;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;

import java.util.List;


@RestController
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/person")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public List<PersonDTO> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("/person/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PersonDTO getPerson(@PathVariable Long id) {
        try {
            return personService.getPerson(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/person")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PersonDTO addPerson(@RequestBody PersonDTORequest personDTO) {
        try {
            return personService.addPerson(personDTO);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IdNotFoundInDatabaseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/person/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public PersonDTO updatePerson(@PathVariable Long id, @RequestBody PersonDTORequest personDTO) {
        try {
            return personService.updatePerson(id, personDTO);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (ItemExistsInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/person/password")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            personService.changePassword(oldPassword, newPassword);
        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/person/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void deletePerson(@PathVariable Long id) {
        try {
            personService.deletePerson(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/person/{id}/keeping")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public Iterable<KeepingDTO> getPersonsKeepings(@PathVariable Long id) {
        try {
            return personService.getPersonsKeepings(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}