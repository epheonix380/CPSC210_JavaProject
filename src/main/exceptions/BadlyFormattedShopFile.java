package exceptions;


//Represents error that occurs when the formatting of the json file is not standard
public class BadlyFormattedShopFile extends Exception {

    public final String location;

    // MODIFIES: This
    // EFFECTS:  location is meant to store where the shop file is located
    public BadlyFormattedShopFile(String location) {
        this.location = location;
    }

}
