package exceptions;

public class NewException extends Exception {

    public NewException(String category, int line, String text) {
        super("Invalid " + category + " entry on line " + line + ", because " + text);
    }
}
