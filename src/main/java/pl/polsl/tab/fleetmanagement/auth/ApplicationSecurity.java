package pl.polsl.tab.fleetmanagement.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    private final UserPrincipalDetailsService userPrincipalDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public ApplicationSecurity(UserPrincipalDetailsService userPrincipalDetailsService,
                               JwtRequestFilter jwtRequestFilter) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userPrincipalDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/**/boss/**").hasAuthority("ROLE_BOSS")
                .antMatchers(HttpMethod.POST, "/person*").hasAnyAuthority("ROLE_ADMIN", "ROLE_BOSS")
                .antMatchers("/swagger-ui.html/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PROGRAMMER")
                .antMatchers("/**/keeper/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_BOSS", "ROLE_KEEPER")
                .antMatchers("/","/login").permitAll()
                .anyRequest().authenticated().
                and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
