package items;

import exceptions.NewException;

public class ItemCategory {
    private int id;
    private String category;
    private int line;

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
