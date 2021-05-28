package pl.polsl.tab.fleetmanagement.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

@Configuration
public class ApplicationSecurityInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
    }
}
