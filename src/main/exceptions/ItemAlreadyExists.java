package exceptions;

public class ItemAlreadyExists extends Exception {

    private static final String pt1 = "The item you attempted to create already exists,";
    private static final String pt2 = " please use edit to change already existing items";
    private boolean isLoad;
    private String context;
    public static final String message = pt1 + pt2;

    public ItemAlreadyExists() {
        isLoad = false;
    }

    public ItemAlreadyExists(String context) {
        isLoad = true;
        this.context = context;
    }

    public String getMessage() {
        if (isLoad) {
            String pt1 = "The ";
            String pt2 = " you attempted to create already exists,";
            String pt3 = " please use load to change already existing ";
            return pt1 + context + pt2 + pt3 + context;
        } else {
            return this.message;
        }
    }
}
