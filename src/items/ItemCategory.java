package items;

import exceptions.NewException;

/**
 * A class for handling the categories in categoryLines
 *
 * @author Christian Steinrück
 * @version 1.0.0
 */
public class ItemCategory {
    private int id;
    private String category;
    private int line;

    /**
     * This constructor creates an ItemCategory object and  throws exception if the input variable are wrong
     *
     * @param id       the id from the categoryLines text file, should be positive
     * @param category the name of the category, should not be empty and should not contain any special character or number
     * @param line     shows the line in the text file
     * @throws NewException will be thrown if the above called bugs are pop up
     */
    public ItemCategory(int id, String category, int line) throws NewException {
        if (id < 0) {
            throw new NewException("Category", line, "id should not be negative");
        }
        if (category == null || category.isEmpty()) {
            throw new NewException("Category", line, "category can't be empty");
        }
        if (category.replaceAll("\\s", "").matches(".*\\d.*")) {
            throw new NewException("Category", line, "category should not contain number");
        }
        if (category.replaceAll("\\s", "").matches(".*\\W.*")) {
            throw new NewException("Category", line, "category should only contain words");
        }

        this.id = id;
        this.category = category;
        this.line = line;
    }


    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getLine() {
        return line;
    }

}
