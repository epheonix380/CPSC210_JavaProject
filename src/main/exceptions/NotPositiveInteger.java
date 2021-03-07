package exceptions;

public class NotPositiveInteger extends Exception {

    private int malInt;

    public NotPositiveInteger(int malInt) {
        this.malInt = malInt;
    }

    public String getMessage() {
        return "The inputted integer " + malInt + " is either zero or smaller. The int needs to be a positive integer";
    }
}
