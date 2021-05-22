package pl.polsl.tab.fleetmanagement.exceptions;

public class ItemExistsInDatabaseException extends RuntimeException {

    public ItemExistsInDatabaseException(String message) {
        super(message);
    }
}
