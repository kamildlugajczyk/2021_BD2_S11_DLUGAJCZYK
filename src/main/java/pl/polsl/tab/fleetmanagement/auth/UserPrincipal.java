package pl.polsl.tab.fleetmanagement.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polsl.tab.fleetmanagement.people.PersonEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final PersonEntity personEntity;

    public UserPrincipal(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String function = this.personEntity.getFunctionsByFunctionsId().getName();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + function);
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.personEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.personEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
