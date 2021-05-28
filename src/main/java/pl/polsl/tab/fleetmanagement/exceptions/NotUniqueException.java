package pl.polsl.tab.fleetmanagement.exceptions;

public class NotUniqueException extends RuntimeException {

    public NotUniqueException(String itemTypeName, String paramName, String paramValue) {
        super(itemTypeName + " (" + paramName + ":" + paramValue + ") exists in database");
    }
}
