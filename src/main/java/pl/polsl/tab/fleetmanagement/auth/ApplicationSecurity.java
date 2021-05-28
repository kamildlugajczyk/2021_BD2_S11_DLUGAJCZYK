package pl.polsl.tab.fleetmanagement.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    private final UserPrincipalDetailsService userPrincipalDetailsService;

    public ApplicationSecurity(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
    {
         auth.authenticationProvider(this.authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // TODO login and logout page (default, success, failure)

        http
            .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
            .and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/**/boss/**").hasAnyAuthority( "ROLE_BOSS")
                .antMatchers(HttpMethod.POST, "/person*").hasAnyAuthority("ROLE_ADMIN", "ROLE_BOSS")
                .antMatchers("/swagger-ui.html/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PROGRAMMER")
                .antMatchers("/**/keeper/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_BOSS", "ROLE_KEEPER")
                .antMatchers("login*").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .defaultSuccessUrl("/swagger-ui.html")
            .and()
            .logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
            .and()
            .sessionManagement()
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .expiredUrl("/login")
                .and()
                .invalidSessionUrl("/login");
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    protected PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }
}
