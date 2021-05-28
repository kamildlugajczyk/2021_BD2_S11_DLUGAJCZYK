package pl.polsl.tab.fleetmanagement.people;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
    PersonEntity findByUsername(String username);
    Boolean existsByUsername(String username);
}
