package exceptions;

public class BadlyFormatedShopFile extends Exception{

    public final String location;

    public BadlyFormatedShopFile(String location) {
        this.location = location;
    }
}
