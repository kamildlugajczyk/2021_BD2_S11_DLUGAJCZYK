package pl.polsl.tab.fleetmanagement.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserPrincipalDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonEntity person = this.personRepository.findByUsername(s);
        return new UserPrincipal(person);
    }
}
