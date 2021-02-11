package errors;

public class ItemNotFoundError extends Error {

    private String item;

    public ItemNotFoundError(String item) {
        this.item = item;
    }

    public String errorMessage() {
        String msg = "The requested item '" + item + "' was not found, please check spelling and try again";
        return msg;
    }
}
