package exceptions;

public class BadlyFormattedShopFile extends Exception {

    public final String location;

    public BadlyFormattedShopFile(String location) {
        this.location = location;
    }
}
