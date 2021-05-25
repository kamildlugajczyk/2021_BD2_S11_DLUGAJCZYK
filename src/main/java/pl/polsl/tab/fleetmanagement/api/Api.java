package pl.polsl.tab.fleetmanagement.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {

    @GetMapping("/api")
    public String version() {
        return "1.0";
    }

}
