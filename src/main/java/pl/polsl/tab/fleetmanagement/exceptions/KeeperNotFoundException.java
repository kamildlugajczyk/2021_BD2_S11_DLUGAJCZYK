package pl.polsl.tab.fleetmanagement.exceptions;

public class KeeperNotFoundException extends RuntimeException {

    public KeeperNotFoundException(String message) {
        super(message);
    }
}