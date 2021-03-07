package exceptions;


//ItemAlreadyExists represents when an item of the same name already exists
public class ItemAlreadyExists extends Exception {

    private static final String pt1 = "The item you attempted to create already exists,";
    private static final String pt2 = " please use edit to change already existing items";
    private boolean withContext;
    private String context;
    public static final String message = pt1 + pt2;

    // MODIFIES: This
    // EFFECTS:  withContext is whether or not this context was used in the initialization
    public ItemAlreadyExists() {
        withContext = false;
    }

    // MODIFIES: This
    // EFFECTS:  context is specific item type that already exists
    //           withContext is whether or not this context was used in the initialization
    public ItemAlreadyExists(String context) {
        withContext = true;
        this.context = context;
    }

    // EFFECTS:  if withContext is true it returns message with context else it returns the standard message
    public String getMessage() {
        if (withContext) {
            String pt1 = "The ";
            String pt2 = " you attempted to create already exists,";
            String pt3 = " please use load to change already existing ";
            return pt1 + context + pt2 + pt3 + context;
        } else {
            return this.message;
        }
    }
}
