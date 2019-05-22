package exceptions;

/**
 * A custom exception class for the handling of the program
 */
public class NewException extends Exception {
    /**
     * This consturctor creates a custom exception
     *
     * @param category is it category or item
     * @param line     which line in the textfile
     * @param text     what broke in the program
     */
    public NewException(String category, int line, String text) {
        super("Invalid " + category + " entry on line " + line + ", because " + text);
    }
}
