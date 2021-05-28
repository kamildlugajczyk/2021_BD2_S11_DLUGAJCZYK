package pl.polsl.tab.fleetmanagement.exceptions;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String itemTypeName, Long id) {
        super(itemTypeName + " (" + id + ") not found in database");
    }
}
