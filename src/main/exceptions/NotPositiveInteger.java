package exceptions;


//NotPositiveInteger represents when a given integer is not positive but a positive integer was expected
public class NotPositiveInteger extends Exception {

    private int malInt;

    // MODIFIES: This
    // EFFECTS:  malInt is the integer that was not positive
    public NotPositiveInteger(int malInt) {
        this.malInt = malInt;
    }

    // EFFECTS: Returns a standard message with malInt
    public String getMessage() {
        return "The inputted integer " + malInt + " is either zero or smaller. The int needs to be a positive integer";
    }
}
