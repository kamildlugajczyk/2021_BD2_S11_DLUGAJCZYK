package pl.polsl.tab.fleetmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotUniqueAdvice {

    @ResponseBody
    @ExceptionHandler(NotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String notUniqueHandler(NotUniqueException ex) {
        System.out.println(ex.getMessage());
        return ex.getMessage();
    }
}
