package pl.polsl.tab.fleetmanagement.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException(String message) {
        super(message);
    }
}